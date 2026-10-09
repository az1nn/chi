#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."
if [[ ! -d .git ]]; then
  echo "ERROR: run from a checked-out Chi git repository" >&2
  exit 1
fi
if [[ -n "$(git status --porcelain)" ]]; then
  echo "ERROR: working tree must be clean to protect existing specs/constitution" >&2
  exit 1
fi
if ! command -v specify >/dev/null 2>&1; then
  echo "ERROR: install official Spec Kit CLI: uv tool install specify-cli" >&2
  exit 1
fi
if [[ -f .specify/integration.json ]]; then
  echo "Spec Kit integration.json already exists; inspect it before reinitializing."
  exit 0
fi
saved_constitution="$(mktemp)"
trap 'rm -f "$saved_constitution"' EXIT
cp .specify/memory/constitution.md "$saved_constitution"

specify init --here --force --non-interactive --script sh --integration copilot --integration-options="--skills" --ignore-agent-tools

# Existing Chi governance is authoritative; initialization must not reset it.
if ! cmp -s "$saved_constitution" .specify/memory/constitution.md; then
  cp "$saved_constitution" .specify/memory/constitution.md
  echo "Restored Chi constitution after upstream template initialization."
fi
echo "Review git diff, validate with python3 scripts/verify_repo.py, then commit generated Spec Kit files."

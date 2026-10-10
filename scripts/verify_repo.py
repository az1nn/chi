"""Minimal stdlib governance checks; not an Android build or runtime test."""
from pathlib import Path
import json
import re
import sys

ROOT = Path(__file__).resolve().parents[1]
REQUIRED = [
    "specs/001-handwriting-ime/research.md",
    "AGENTS.md",
    ".github/skills/siga/SKILL.md",
    ".github/skills/android-ime/SKILL.md",
    ".specify/memory/constitution.md",
    ".specify/integration.json",
    ".specify/init-options.json",
    ".specify/scripts/bash/check-prerequisites.sh",
    ".specify/templates/spec-template.md",
    ".specify/workflows/speckit/workflow.yml",
    "specs/001-handwriting-ime/spec.md",
    "specs/001-handwriting-ime/plan.md",
    "specs/001-handwriting-ime/tasks.md",
    "docs/adr/0001-native-ime-boundaries.md",
    "docs/handoffs/chi-foundation.md",
]
errors = []
for rel in REQUIRED:
    file = ROOT / rel
    if not file.is_file() or not file.read_text(encoding="utf-8").strip():
        errors.append(f"missing/empty: {rel}")

for name in ("siga", "android-ime"):
    path = ROOT / f".github/skills/{name}/SKILL.md"
    if path.is_file():
        data = path.read_text(encoding="utf-8")
        if not data.startswith(f"---\nname: {name}\n"):
            errors.append(f"skill YAML frontmatter name missing: {name}")
        if not re.search(r"(?m)^description:\s*\S+", data):
            errors.append(f"skill YAML frontmatter description missing: {name}")
        if "\n---\n" not in data[4:]:
            errors.append(f"skill YAML frontmatter close missing: {name}")

spec = ROOT / "specs/001-handwriting-ime/spec.md"
tasks = ROOT / "specs/001-handwriting-ime/tasks.md"
if spec.is_file() and "OFFLINE-001" not in spec.read_text(encoding="utf-8"):
    errors.append("spec missing model offline human gate OFFLINE-001")
if tasks.is_file() and not re.search(r"(?m)^- \[x\] T005\b", tasks.read_text(encoding="utf-8")):
    errors.append("T005 must be checked after OFFLINE-001=B approval")
for rel in (
    "specs/001-handwriting-ime/spec.md",
    "specs/001-handwriting-ime/plan.md",
    "specs/001-handwriting-ime/tasks.md",
    "docs/adr/0001-native-ime-boundaries.md",
    ".specify/memory/constitution.md",
    "docs/handoffs/chi-foundation.md",
):
    path = ROOT / rel
    if path.is_file() and not re.search(r"OFFLINE-001\s*(?:=|—|\s)[^\n]{0,90}B", path.read_text(encoding="utf-8")):
        errors.append(f"missing OFFLINE-001 decision B trace: {rel}")
if (ROOT / ".github/skills/godot").exists():
    errors.append("unrelated Godot skill must not be copied into Chi")

# Assert this was initialized by the official Spec Kit Copilot skills integration.
# This is still a static repository check, not proof of Android runtime.
CORE_SPEC_KIT_SKILLS = (
    "speckit-constitution",
    "speckit-specify",
    "speckit-clarify",
    "speckit-plan",
    "speckit-tasks",
    "speckit-analyze",
    "speckit-implement",
    "speckit-converge",
)
for name in CORE_SPEC_KIT_SKILLS:
    path = ROOT / f".github/skills/{name}/SKILL.md"
    if not path.is_file():
        errors.append(f"official Spec Kit skill missing: {name}")
    elif f'name: "{name}"' not in path.read_text(encoding="utf-8"):
        errors.append(f"official Spec Kit skill frontmatter mismatched: {name}")

integration_path = ROOT / ".specify/integration.json"
if integration_path.is_file():
    try:
        integration = json.loads(integration_path.read_text(encoding="utf-8"))
        if integration.get("version") != "0.12.11":
            errors.append("unexpected Spec Kit version: expected audited 0.12.11")
        if integration.get("default_integration") != "copilot":
            errors.append("official Spec Kit integration must remain Copilot")
        options = integration.get("integration_settings", {}).get("copilot", {}).get("parsed_options", {})
        if options.get("skills") is not True:
            errors.append("official Spec Kit Copilot integration must use skills mode")
    except (ValueError, TypeError, AttributeError) as exc:
        errors.append(f"invalid Spec Kit integration metadata: {exc}")

tasks_status = ROOT / "specs/001-handwriting-ime/tasks.md"
if tasks_status.is_file() and not re.search(r"(?m)^- \[x\] T004\b", tasks_status.read_text(encoding="utf-8")):
    errors.append("T004 must be complete after official generated Spec Kit import")

if errors:
    for error in errors:
        print("FAIL:", error)
    sys.exit(1)
print("PASS: Chi repository foundation contracts (documentation only; no Android build)")

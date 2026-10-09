# Chi — Agent contract

## Priority and sources
1. Actual GitHub/local worktree state, exact HEAD, CI/tests and Android runtime evidence.
2. Current files in `specs/`, `.specify/memory/constitution.md`, ADRs and `docs/handoffs/`.
3. Chat and remembered intent only as hints.

## Routing
- Standalone **Siga** → load `.github/skills/siga/SKILL.md`, then consult its external canonical source. RECONCILE → CLASSIFY → EXECUTE → VERIFY → HANDOFF.
- **Android IME**, Kotlin/SDK/stylus/recognizer topics → load `.github/skills/android-ime/SKILL.md` within SIGA's scope.
- **Spec Kit** → use upstream-generated `speckit.*` workflows after running `scripts/bootstrap-spec-kit.sh`; do not make duplicate local Spec Kit command implementations.

## Operating constraints
- Repository `az1nn/chi` is the only authoritative location for Chi's product specs and state.
- The SIGA protocol is canonical in `az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md`; the local SIGA skill is only a routing adapter.
- Do not import SIGA's **repository-specific standing auto-merge permission** from cpxlabs-admin. On Chi, merge requires its own verified authorization and gates.
- Material development: spec → clarification → plan → tasks → implementation → verification → convergence.
- Never claim Android build, app runtime, offline recognition or UI acceptance from documentation-only checks.
- Do not create parallel sessions or branches if an active Chi PR/workstream already owns the scope.
- No uploading raw strokes, keystrokes, sensitive input, or learned text without explicit scope/consent.
- A blocking product choice remains `NEEDS CLARIFICATION`; it cannot be silently resolved by convenience.

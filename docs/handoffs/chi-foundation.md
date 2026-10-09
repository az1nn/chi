CAVEMAN HANDOFF v1

APP: Chi — native Kotlin Android handwriting keyboard
WORKSTREAM: Feature 001 foundation, skills + Spec Kit integration
STATE: FOUNDATION PR / SPEC DRAFT; runtime NOT IMPLEMENTED
MODE: ADVANCE (at creation; reclassify against live GitHub state)
CANONICAL SOURCE: az1nn/chi current Git state + specs/001-handwriting-ime + .specify/memory/constitution.md
CURRENT VERSION / HEAD: Determine from live branch and PR on each session
BASE: master
BRANCH / ENV: feat/001-chi-skills-speckit; Android app not created
PR / MR / TASK: Reconcile live; no PR number baked into this file
SPEC / ADR: specs/001-handwriting-ime/* ; docs/adr/0001-native-ime-boundaries.md

DONE: Chi repo scaffold, SIGA adapter, Android IME skill, Spec Kit product artifacts, governance checks (subject to actual PR/CI verification).
VERIFY: python3 scripts/verify_repo.py; inspect exact-head PR CI after opening
GATES: OFFLINE-001 human decision; local official Spec Kit CLI bootstrap; Android build N/A
BLOCKERS: Recognizer packaging choice; no Android runtime/toolchain in repo
INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT; no false build PASS; no inherited auto-merge
NEXT: Resolve OFFLINE-001; bootstrap official GitHub Spec Kit in local checkout; implement Feature 001 task T006 with tests
VERIFY-FIRST: Read current repo tree, exact HEAD, open PR, checks, specs/tasks/constitution; reconcile before choosing RESUME/WATCH/ADVANCE.

CAVEMAN HANDOFF v1

APP: Chi — native Kotlin Android handwriting keyboard
WORKSTREAM: Feature 001 foundation, skills + Spec Kit integration
STATE: FOUNDATION PR / SPEC DRAFT; runtime NOT IMPLEMENTED
MODE: RESUME (when updated; existing draft PR #1 has remaining tasks; reclassify against live state)
CANONICAL SOURCE: az1nn/chi current Git state + specs/001-handwriting-ime + .specify/memory/constitution.md
CURRENT VERSION / HEAD: Determine from live branch and PR on each session
BASE: master
BRANCH / ENV: feat/001-chi-skills-speckit; Android app not created
PR / MR / TASK: #1 Draft; T005 done; T004/T006+ pending; recheck status, checks and HEAD live
SPEC / ADR: specs/001-handwriting-ime/* ; docs/adr/0001-native-ime-boundaries.md

DONE: Chi repo scaffold, SIGA adapter, Android IME skill, Spec Kit product artifacts; OFFLINE-001=B approved and recorded in spec/plan/tasks/ADR/constitution. Previous HEAD governance checks passed; new HEAD CI must be verified independently.
VERIFY: python3 scripts/verify_repo.py; inspect exact-head PR CI after opening
GATES: OFFLINE-001 satisfied (B on 2026-10-09); official Spec Kit CLI bootstrap T004 pending; recognizer SDK validation T013 pending; PR review/merge remains gated; Android build N/A
BLOCKERS: Official Spec Kit integration T004 not executed in user's local checkout; recognizer implementation waits on verified adapter T013; no Android runtime/toolchain in repo
INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT; no false build PASS; no inherited auto-merge
NEXT: Recheck PR #1 exact HEAD and CI; execute official GitHub Spec Kit bootstrap T004 in a clean checkout, validate generator output; continue adapter T013 with runtime/license/first-download proof; documentary pt-BR evidence exists in specs/001-handwriting-ime/research.md; only then begin Kotlin T006 with tests
VERIFY-FIRST: Read current repo tree, exact HEAD, open PR, checks, specs/tasks/constitution; reconcile before choosing RESUME/WATCH/ADVANCE.

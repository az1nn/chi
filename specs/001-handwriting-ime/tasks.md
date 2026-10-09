# Tasks — Feature 001

**State:** PLANNED. None of these are marked implemented.

## Phase 0 — Governance / setup
- [x] T001 [P] Record Chi constitution and first feature spec, plan, task ledger in Git.
- [x] T002 [P] Add portable SIGA adapter and Android IME specialist skill.
- [x] T003 Create repository document guardrails and a reviewable foundation PR.
- [ ] T004 Run official Spec Kit CLI bootstrap on a local checkout and commit generated integration/templates.
- [ ] T005 Resolve OFFLINE-001 and update spec, plan, ADR and tasks before implementation.

## Phase 1 — Minimum native keyboard (US1)
- [ ] T006 Initialize Android/Kotlin Gradle app, build and lint configuration.
- [ ] T007 Register InputMethodService and IME metadata.
- [ ] T008 Implement a setup/enable/switch keyboard flow and device instrumentation tests.

## Phase 2 — Writing and editor safety (US2, US3)
- [ ] T009 Implement InkCanvas and ordered strokes with deterministic tests.
- [ ] T010 Implement EditorGateway/active InputConnection and sensitive-input tests.
- [ ] T011 Implement candidate, space, delete and clear UI, with accessibility checks.
- [ ] T012 Implement RecognitionCoordinator cancellation and no-stale-commit tests.

## Phase 3 — Recognition (US2, US4)
- [ ] T013 Select recognizer after OFFLINE-001, prove pt-BR support and dependency policy.
- [ ] T014 Implement InkRecognizer adapter/model readiness and error handling.
- [ ] T015 Prove airplane-mode recognition with a locally ready model; verify cold-install requirement per decision.
- [ ] T016 Measure pen-up to candidate latency on declared devices/corpus.

## Phase 4 — End-to-end quality (US1–US5)
- [ ] T017 Run complete stylus-to-editor journeys in a real supported device.
- [ ] T018 Execute and report lint, unit, instrumentation, build and privacy checks.
- [ ] T019 Converge spec / plan / tasks / ADR / observed behavior; final handoff.

## Dependencies
T004/T005 precede feature implementation; T006..T012 precede T014..T018; T013 is blocked by OFFLINE-001. Do not advance blocked tasks by marking them done without evidence.

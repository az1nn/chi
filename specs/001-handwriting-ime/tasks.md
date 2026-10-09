# Tasks — Feature 001

**State:** PLANNED; governance T001–T003 and decision T005 completed. Android runtime not implemented.

## Phase 0 — Governance / setup
- [x] T001 [P] Record Chi constitution and first feature spec, plan, task ledger in Git.
- [x] T002 [P] Add portable SIGA adapter and Android IME specialist skill.
- [x] T003 Create repository document guardrails and a reviewable foundation PR.
- [ ] T004 Run official Spec Kit CLI bootstrap on a local checkout and commit generated integration/templates.
- [x] T005 Resolve OFFLINE-001 = B (approved 2026-10-09) and update spec, plan, ADR and tasks; no vendor selection implied.

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
- [ ] T013 Evaluate and select an on-device recognizer compatible with OFFLINE-001=B; verify pt-BR support, initial model download API/UX, dependencies, licensing, privacy and offline evidence. Decision gate cleared, technical selection pending.
- [ ] T014 Implement InkRecognizer adapter/model readiness and error handling.
- [ ] T015 Prove airplane-mode recognition with a ready local model; on cold install without a model, assert an explicit setup state and no upload/cloud inference; test download failure, retry and recovery.
- [ ] T016 Measure pen-up to candidate latency on declared devices/corpus.

## Phase 4 — End-to-end quality (US1–US5)
- [ ] T017 Run complete stylus-to-editor journeys in a real supported device.
- [ ] T018 Execute and report lint, unit, instrumentation, build and privacy checks.
- [ ] T019 Converge spec / plan / tasks / ADR / observed behavior; final handoff.

## Dependencies
T005 is complete (OFFLINE-001=B); T004 (official Spec Kit CLI bootstrap) remains incomplete and precedes Kotlin implementation. T013 vendor research is unblocked but requires evidence before T014 adapter implementation. T006..T012 precede T014..T018. Preserve uncompleted tasks and gate evidence; never claim an Android build or usable IME before device tests.

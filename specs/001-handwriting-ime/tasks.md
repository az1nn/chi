# Tasks — Feature 001

**State:** IMPLEMENTING; T001–T007 completed at source/build level. Android IME device activation, ink and recognition are not verified/implemented.

## Phase 0 — Governance / setup
- [x] T001 [P] Record Chi constitution and first feature spec, plan, task ledger in Git.
- [x] T002 [P] Add portable SIGA adapter and Android IME specialist skill.
- [x] T003 Create repository document guardrails and a reviewable foundation PR.
- [x] T004 Run official GitHub Spec Kit CLI 0.12.11 in a clean GitHub Actions checkout and commit upstream-generated Copilot skills, templates, scripts and manifests (commit `6319c7a`; run `37991571999` SUCCESS); no hand-copied commands.
- [x] T005 Resolve OFFLINE-001 = B (approved 2026-10-09) and update spec, plan, ADR and tasks; no vendor selection implied.

## Phase 1 — Minimum native keyboard (US1)
- [x] T006 Initialize Android/Kotlin Gradle app, build and lint configuration. JDK 17 / Gradle 8.13 / AGP 8.13.2 / Kotlin 2.3.10 / SDK 36; Android CI `37995370625` PASS (`assembleDebug`, `lintDebug`, `testDebugUnitTest`).
- [x] T007 Register `InputMethodService`, manifest and IME subtype metadata and minimal explicit text controls. Compile/lint PASS at `1999d90f`; **enable/select/device behavior still T008**, not claimed.
- [ ] T008 Implement a setup/enable/switch keyboard flow and device instrumentation tests. **Onboarding source and emulator test workflow committed at 9eab7198; completion requires green emulator CI plus focus/interaction evidence.**

## Phase 2 — Writing and editor safety (US2, US3)
- [ ] T009 Implement InkCanvas and ordered strokes with deterministic tests.
- [ ] T010 Implement EditorGateway/active InputConnection and sensitive-input tests.
- [ ] T011 Implement candidate, space, delete and clear UI, with accessibility checks.
- [ ] T012 Implement RecognitionCoordinator cancellation and no-stale-commit tests.

## Phase 3 — Recognition (US2, US4)
- [ ] T013 Evaluate and select an on-device recognizer compatible with OFFLINE-001=B; verify pt-BR support, initial model download API/UX, dependencies, licensing, privacy and offline evidence. Documentary feasibility in `research.md`; vendor selection and runtime evidence still pending.
- [ ] T014 Implement InkRecognizer adapter/model readiness and error handling.
- [ ] T015 Prove airplane-mode recognition with a ready local model; on cold install without a model, assert an explicit setup state and no upload/cloud inference; test download failure, retry and recovery.
- [ ] T016 Measure pen-up to candidate latency on declared devices/corpus.

## Phase 4 — End-to-end quality (US1–US5)
- [ ] T017 Run complete stylus-to-editor journeys in a real supported device.
- [ ] T018 Execute and report lint, unit, instrumentation, build and privacy checks.
- [ ] T019 Converge spec / plan / tasks / ADR / observed behavior; final handoff.

## Dependencies
T004 (official Spec Kit integration), T005 (OFFLINE-001=B), T006 (Kotlin Gradle baseline) and T007 (IME registration/source) are complete with their stated evidence; T008 (real enable/switch onboarding and instrumentation) is next. T013 vendor research is unblocked but requires evidence before T014 adapter implementation. T006..T012 precede T014..T018. Preserve uncompleted tasks and gate evidence; never claim an Android build or usable IME before device tests.

## Visual design gate (supplemental to T008/T009/T011/T013–T018)
- [x] Candidate concept documented: [CHI-ART-CONCEPT-001](../../docs/design/CHI-ART-CONCEPT-001.md), V2.1 **APPROVED VISUAL** (not runtime).
- [x] Visual human approval of composition, core flows and components (VG-01 / VG-02) on 2026-10-10: unified dark-first IME, fixed microphone and approved light variant.
- [ ] Once implemented, validate real UI in portrait/landscape/dark/font-scale, accessibility, IME focus safety and actual device screenshots (VG-03..VG-05).
- Design review does not mark any Kotlin build, recognition or hardware verification task complete.

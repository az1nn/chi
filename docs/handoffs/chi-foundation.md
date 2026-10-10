CAVEMAN HANDOFF v1

APP: Chi — native Android Kotlin IME (handwriting + planned offline voice)
WORKSTREAM: Feature 001 PR #1 / T008 onboarding and emulator smoke; Feature 002 V2.1 visual
STATE: PR #1 OPEN DRAFT, no merge; T008 onboarding source added but emulator completion not yet proven; handwriting and audio recognition absent
MODE: RESUME for failed emulator smoke (prior failure diagnosed and workflow fix dispatched; recheck new exact-HEAD CI)
CANONICAL SOURCE: az1nn/chi PR #1 live HEAD, specs/001-handwriting-ime/*, specs/002-voice-transcription/*, docs/design/CHI-ART-CONCEPT-001.md; SIGA canonical az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md

CURRENT VERSION / HEAD: previous code/docs SHA ea523db166013426a9586c6c8e096f74a39e2894; this handoff and CI correction commit follows; use PR #1 live HEAD
BASE: master (base SHA 1f0ce7beb9fa1bd7ac50d106be290cccf2dcff4f at reconciling)
BRANCH / ENV: feat/001-chi-skills-speckit, GitHub Actions JDK 17 / Gradle 8.13 / Android SDK 36 / emulator API 35
PR / MR / TASK: PR #1 Draft; T001–T007 source PASS; T008 PARTIAL/OPEN
SPEC / ADR: SPEC-001 with OFFLINE-001 = B, SPEC-002 voice engine/open offline gate, ADR-0001 PROPOSED, constitution v0.1.0 DRAFT

DONE: setup Activity for explicit Android IME enable/picker/keyboard status/test EditText; instrumentation verification test; emulator workflow; V2.1 approved visual baseline recorded in Feature 001/002 specs. Both handwritten ink and voice will live on ONE IME panel; fixed microphone; dark-first, light secondary. VG-01/VG-02 human visual PASS.
VERIFY: prior HEAD e33cb42 Android/governance PASS. HEAD ea523db Android assembleDebug/lintDebug/testDebugUnitTest + governance PASS (runs 38065051823, 38065051912); emulator run 38065051823 FAIL at adb shell ime list -s grep before enabling; emulator boot, app and test-APK compilation and app installation SUCCESS, instrumentation NOT EXECUTED. Cause: ime list without -a lists only enabled IMEs. Workflow corrected to list -a -s BEFORE enable, verify enabled list after enable, then select + run tests. New HEAD requires fresh CI.
GATES: T008 emulator instrumentation, actual user picker/focus behavior and device test OPEN. VG-01/VG-02 PASS visually; VG-03/04/05 technical/runtime/device OPEN. Voice engine VOICE-ENGINE-001 and VOICE-OFFLINE-001 OPEN; handwriting recognizer T013 OPEN; ADR and constitution not ratified. No Chi-specific merge authorization.
BLOCKERS: no verified Android device input interaction nor recognition/stylus, offline/model or microphone behavior.
INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT; VERIFY-FIRST; OFFLINE-001 = B means explicit one-time handwriting model download allowed then on-device only; no strokes/audio upload; no inherited auto-merge; never infer device validation from build.
NEXT: check exact PR HEAD; consume new Android/governance/emulator CI; on fail diagnose and fix real issue. Keep T008 open until emulator and focus evidence; then T009 InkCanvas and T010 editor safety in the approved layout, without adopting unapproved engine.
VERIFY-FIRST: fetch PR #1 head, checks, job logs, recent concurrent commits, docs/skills/ADR/specs, verify no competing work, rerun/inspect emulator and real device acceptance before claiming T008 PASS.

CAVEMAN HANDOFF v1

APP: Chi — native Android Kotlin IME (handwriting + planned offline voice)
WORKSTREAM: Feature 001 PR #1 / T008 Android onboarding; Feature 002 V2.1 visual
STATE: PR #1 OPEN DRAFT, not merge-authorized; T008 PARTIAL; handwritten ink/recognition and speech recognition not implemented.
MODE: RESUME (real-device keyboard enable/select and editor interaction unverified)
CANONICAL SOURCE: az1nn/chi PR #1 live HEAD, specs/001-handwriting-ime/*, specs/002-voice-transcription/*, docs/design/CHI-ART-CONCEPT-001.md; SIGA canonical az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md

CURRENT VERSION / HEAD: verify live PR #1 HEAD; code tested at c5f1f32dad94793b5b0d0fd03ec415fd766b6aac; subsequent doc-only changes recorded after that run.
BASE: master (reverify live)
BRANCH / ENV: feat/001-chi-skills-speckit; GitHub Actions JDK 17 / Gradle 8.13 / Android SDK 36 / emulator API 35; Android device model/version not reported.
PR / MR / TASK: PR #1 Draft; T001–T007 source PASS; T008 PARTIAL/OPEN, not completed.
SPEC / ADR: SPEC-001 OFFLINE-001=B approved; SPEC-002 voice engine/offline OPEN; ADR-0001 PROPOSED; constitution v0.1.0 DRAFT.

DONE: setup Activity supports explicit Android IME enable/picker/status and local EditText. Latest tested CI at c5f1f32: Android build/lint/unit and emulator ime-emulator-smoke SUCCESS (run 38065410516); governance SUCCESS (run 38065410555). Human operator stated on 2026-10-11 that experimental APK installed and app opened correctly on a physical Android device. This is HUMAN-REPORTED INSTALL+LAUNCH PASS ONLY; device details, screenshots, IME selection, editor focus/input, and privacy behavior were NOT supplied or verified.
VERIFY: GitHub Actions run 38065410516 completed with android and ime-emulator-smoke SUCCESS; run 38065410555 governance SUCCESS. Prior emulator failure on run 38065051823 corrected with ime list -a -s and rerun green. Human-reported hardware install/launch passed. Recheck CI at new document HEAD; no fresh runtime evidence should be inferred from docs commits.
GATES: T008 remains OPEN until device-level enable/select + external editable focus/input and sensitive editor behavior are evidenced. VG-01/VG-02 visual PASS; VG-03/04/05 runtime/a11y/device OPEN. Handwriting recognizer T013, VOICE-ENGINE-001 and VOICE-OFFLINE-001 OPEN. Constitution and ADR remain unratified; no Chi-specific merge approval.
BLOCKERS: no verified real-device keyboard selection or text entry; ink/recognition/stylus/audio functionality absent. Device OS and model unreported.

INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT; VERIFY-FIRST; OFFLINE-001=B allows an explicit initial handwriting model download followed by on-device inference only; no stroke/audio upload; no inherited auto-merge authorization; do not equate app launch with functional IME or microphone/stylus success.
NEXT: Obtain physical Android evidence of enabling/selecting Chi, focusing the onboard test EditText then an external app editor, space/delete/switch behavior, and sensitive-input safety. Complete and mark T008 only with required evidence; then advance T009 InkCanvas and T010 editor safety within V2.1 approved unified dark-first panel. No recognition vendor/voice engine implementation before their own decision gates.

VERIFY-FIRST: inspect live PR #1 HEAD, branch/base, Actions at exact HEAD, T008 task ledger and PR comments, device acceptance evidence, and concurrent updates; classify RESUME/WATCH/ADVANCE anew.

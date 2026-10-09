CAVEMAN HANDOFF v1

APP: Chi — Kotlin native Android handwriting IME
WORKSTREAM: Feature 001 / PR #1 / Android T006–T007 bootstrap, T008 next
STATE: PR #1 DRAFT OPEN; T001–T007 source/build-level DONE; T008–T019 pending except documentary T013 progress; visual concept PROPOSED
MODE: RESUME
CANONICAL SOURCE: az1nn/chi exact Git PR HEAD + specs/001-handwriting-ime/{spec,plan,tasks,research}.md + docs/adr/0001-native-ime-boundaries.md; SIGA procedure az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md

CURRENT VERSION / HEAD: 1999d90f6d073b8220fe073fe37419c84c07d60b passed Android/Governance CI before this handoff; RECHECK new HEAD and exact-HEAD runs after this documentation/artifact commit
BASE: master (live verified; recheck)
BRANCH / ENV: feat/001-chi-skills-speckit; GitHub-hosted Ubuntu 24.04 with SDK/Gradle; no emulator or stylus device proof
PR / MR / TASK: #1 Draft, T006/T007 DONE at source+CI level; T008 next; T013 recognizer selection gate open; design VG-01/VG-02 pending
SPEC / ADR: SPEC-001, OFFLINE-001=B, constitution 0.1.0 DRAFT, ADR-0001 PROPOSED, design CHI-ART-CONCEPT-001 PROPOSED

DONE: Reconciled PR #1 and last real code HEAD before changes. Preserved official Spec Kit 0.12.11, SIGA adapter, OFFLINE-001=B. Added single-module Android Kotlin project: AGP 8.13.2, Gradle 8.13, Kotlin 2.3.10, JDK 17, compile/target SDK 36, min SDK 26. Registered InputMethodService/IME metadata, API-26-compatible system keyboard picker, space and backspace; basic sensitive-editor classification and unit tests. No INTERNET permission, no model SDK, no ink acquisition. Concurrent ARTIST commits added PROPOSED design reference in plan/tasks; preserved those changes.
VERIFY: Governance CI PASS on code HEAD 1999d90f (runs 37995370686 and 37995377179). Android run 37995370625 SUCCESS (assembleDebug, lintDebug, testDebugUnitTest) on exact code HEAD. Earlier CI failures exposed deprecated Kotlin jvmTarget DSL and minSdk-26 NewApi call; both corrected, no checks suppressed. Subsequent HEAD requires new CI validation; device behavior, screenshots, apk install/IME enabling, privacy packet capture, latency, offline recognition NOT VERIFIED.
GATES: OFFLINE-001=B satisfied, not vendor approval. Constitution 0.1.0 still draft/unratified. T013 licensing/metrics/privacy/model acceptance pending. Visual human gate VG-01/VG-02 pending. T008/T017 Android device tests pending. Merge requires dedicated Chi human authorization and green exact-HEAD CI; no inherited auto-merge permission.
BLOCKERS: No Android emulator/hardware runtime evidence; no committed Gradle wrapper (CI pins Gradle 8.13); no recognizer vendor accepted; visual concept proposed only.

INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT; SIGA canon stays in cpxlabs-admin; no cloud inference or handwriting upload, no hidden SDK metrics, no stale result commits; no fake test/build claims or gate bypass.
NEXT: Recheck exact HEAD/CI and incoming concurrent changes. T008 implement explicit IME onboarding/enable/switch flow and emulator instrumentation with focus/sensitive input checks. Validate Gradle wrapper option as separate repeatable enhancement. Preserve T013 blocked on privacy/vendor/device evidence and VG human review. Never merge PR #1 automatically.
VERIFY-FIRST: Inspect live PR #1 refs/reviews/runs; Android and governance workflow results on exact HEAD; current README/plan/tasks/ADR/design/handoff; detect competing writes; test physical/emulator IME registration and switching before reporting T008 done.

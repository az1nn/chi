CAVEMAN HANDOFF v1

APP: Chi — native Kotlin Android handwriting IME
WORKSTREAM: Feature 001 PR #1; T008 physical verification, T009 canvas, T010 editor safety, T012 cancellation/coordinator
STATE: PR #1 OPEN DRAFT. T008 physical OPEN; T009/T010 source-only verified in previous CI; T011 not started; T012 SOURCE IMPLEMENTED, exact-HEAD CI PENDING.
MODE: RESUME
CANONICAL SOURCE: Live az1nn/chi PR #1; specs/001-handwriting-ime/, docs/qa/CHI-TEST-MATRIX-001.md; canonical SIGA az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md

CURRENT VERSION / HEAD: Inspect current feat/001-chi-skills-speckit HEAD; do not assume SHA from this handoff.
BASE: master (verify live)
BRANCH / ENV: feat/001-chi-skills-speckit / GitHub Actions JDK17, Android API35 emulator
PR / MR / TASK: PR #1 DRAFT; T001–T007 done, T008 physical OPEN, T009–T010 source complete, T011 OPEN, T012 source committed pending CI/integration
SPEC / ADR: OFFLINE-001=B approved; constitution DRAFT, ADR-0001 PROPOSED; vendor and voice choices OPEN

DONE: Exact prior HEAD 2ac87ec passed Android assemble/lint/unit + emulator enable/select/instrumentation (run 38249730417) and governance (run 38249730784). T012 adds pure Kotlin async InkRecognizer contract + RecognitionCoordinator with invalidation, stale-callback suppression, bounded candidates, editor-epoch guard, explicit single-use selection; 11 JUnit cases. No recognizer adapter or UI integration, no network/SDK/model/voice changes.
VERIFY: Recheck NEW HEAD PR, Android CI and governance. Prior PASS only applies to 2ac87ec. T012 exact-HEAD tests unknown until CI runs.
GATES: T008 physical enable/select/external editor/privacy; T009 stylus/clear/focus visual; T010 InputConnection device interactions; T011 candidate panel/a11y; T012 UI integration; T013 recognizer choice; VG-03..05; merge NOT AUTHORIZED.
BLOCKERS: Real-device model/Android/screenshots, pt-BR recognition vendor/license/download policy, Feature 002 voice engine/offline policy.

INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT. No implicit merge. OFFLINE-001=B; no raw ink or recognized text transmitted; mic disabled. Candidate commit must call live EditorGateway and be explicitly initiated; stale commits prohibited.
NEXT: Check exact new HEAD CI. If green, advance T011 reviewable candidate/Inserir UI wiring, keeping Insert disabled without a recognizer; wire coordinator invalidation to ink/focus/IME lifecycle before enabling any adapter. Collect T008/T009 physical evidence independently.
VERIFY-FIRST: PR #1 head/ownership, CI workflows and steps, Kotlin recognition + editor/ink files, current task matrix, handoff and human gates.

CAVEMAN HANDOFF v1

APP: Chi — native Kotlin Android handwriting IME
WORKSTREAM: Feature 001 PR #1: T008 physical device; T009 stylus canvas; T010 editor safety
STATE: PR #1 OPEN DRAFT, no merge authorization. T008 PARTIAL/OPEN; T009 SOURCE IMPLEMENTED/VERIFY PENDING; T010 SOURCE IMPLEMENTED/VERIFY PENDING.
MODE: RESUME
CANONICAL SOURCE: Live az1nn/chi PR #1, specs/001-handwriting-ime/, docs/qa/CHI-TEST-MATRIX-001.md; SIGA canonical az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md

CURRENT VERSION / HEAD: inspect current feat/001-chi-skills-speckit HEAD, including 2026-10-12 T010/CI patch; never assume SHA from handoff
BASE: master (verify live)
BRANCH / ENV: feat/001-chi-skills-speckit / GitHub Actions JDK17, Android API35 emulator
PR / MR / TASK: PR #1 Draft; T001–T007 completed; T008 physical OPEN; T009/T010 source-only
SPEC / ADR: OFFLINE-001=B approved; constitution DRAFT, ADR-0001 PROPOSED; vendor and voice OPEN

DONE: T009 bounded InkSession, stylus canvas, lifecycle/sensitive-field clearing. T010 pure Kotlin EditorGateway + six deterministic unit cases; live InputConnection resolution for space/delete, focus tokens for future candidate commits, fail-closed unknown/sensitive/stale. CI script polls IME registration up to 30 seconds after install with diagnostics; enable/set/instrumentation remain mandatory. No model, candidate UI, speech runtime or network added.
VERIFY: At prior 029e2d1 governance PASS run 38149242748. Android run 38149242750 build/lint/unit PASS, but emulator job FAILED immediately after APK installation because service was missing from ime list; enable/set/instrumentation NOT executed. Earlier f17657f run 38149075305 Android PASS. Check NEW patch exact-HEAD CI before PASS claims. Physical APK installation/open reported only for older build.
GATES: New HEAD Android and governance CI OPEN; T008 real-device enable/select/focus/privacy OPEN; T009 stylus/clear/focus/sensitive visuals OPEN; T010 runtime OPEN; VG-03..05 OPEN. PR merge unauthorized.
BLOCKERS: physical model/version/screenshots; T013 pt-BR vendor/license/download decision; Feature 002 voice engine and offline decision.

INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT. OFFLINE-001=B; no ink/voice uploads; stale candidate commits forbidden; microphone disabled; never claim app open proves keyboard usability; never auto-merge.
NEXT: Reconcile CI of exact new HEAD and investigate any emulator discovery failure via diagnostic output. Advance T011/T012 only inside active PR after verified state; capture T008/T009 device acceptance separately.
VERIFY-FIRST: PR #1/head, workflows/jobs, test matrix, Kotlin editor/canvas files, prior human gates, concurrent writers.

CAVEMAN HANDOFF v1

APP: Chi — Kotlin Android native IME
WORKSTREAM: Feature 001 PR #1 T008 onboarding + T009 handwriting capture; Feature 002 visual/voice seam
STATE: PR #1 OPEN DRAFT; T008 PARTIAL/OPEN; T009 SOURCE IMPLEMENTED/VERIFY PENDING; no handwriting recognition or voice runtime
MODE: RESUME
CANONICAL SOURCE: az1nn/chi live PR #1, specs/001-handwriting-ime/, docs/qa/CHI-TEST-MATRIX-001.md; SIGA canonical az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md

CURRENT VERSION / HEAD: T009 source implementation through f17657f253849ee7f8683e9a24daa6b1641b2bbd; subsequent documentation commit requires live HEAD check
BASE: master (reverify live)
BRANCH / ENV: feat/001-chi-skills-speckit; GitHub Actions Android JDK 17, API 35 emulator
PR / MR / TASK: PR #1 Draft; T001–T007 source PASS; T008 PARTIAL; T009 SOURCE IMPLEMENTED, CI + device not yet approved
SPEC / ADR: OFFLINE-001=B; SPEC-001 plan/tasks updated; constitution DRAFT, ADR-0001 PROPOSED; SPEC-002 engine/offline OPEN

DONE: T009 InkSession bounded in-memory points/strokes, clear/cancel/undo, immutable snapshots, 7 deterministic unit tests; InkCanvasView stylus-only provisional input, palm/finger ignored, drawing in same dark IME panel; fixed mic visible but disabled; local ink cleared on editor lifecycle/sensitive state. Commits 80c2a31 and f17657f. User reported installing and opening *previous* debug APK successfully on 2026-10-11.
VERIFY: prior exact tested code c5f1f32: assemble/lint/unit and emulator smoke SUCCESS in Actions run 38065410516, governance SUCCESS in 38065410555. New T009 code CI run 38149075305 queued at initial check and governance run 38149075269 in progress; fetch current outcomes for all new HEADs. No T009 runtime PASS claimed.
GATES: T008 real-device keyboard activation/focus/sensitive inputs OPEN; T009 current CI + stylus device visual acceptance OPEN. VG-03/04/05 OPEN; T010–T012 and T013 vendor decision OPEN; no auto-merge authorization.
BLOCKERS: device model/version and keyboard/ink evidence missing; offline recognizer/vendor not selected; voice engine not selected.

INVARIANTS: REAL STATE > HANDOFF > MEMORY > CHAT. Never label app launch as active keyboard/ink recognition. OFFLINE-001=B means explicit model download then offline local inference; no ink/voice uploads; no merge while explicit manual acceptance unresolved.
NEXT: Verify exact HEAD Android jobs. Fix legitimate T009 CI failures if present. Independently plan/implement T010 focus-safe EditorGateway with tests; keep human T008/T009 evidence on test matrix. Do not select recognizer or enable voice without separate decision.
VERIFY-FIRST: live HEAD + PR state, CI jobs, files, concurrency, testing matrix and any new device acceptance before deciding RESUME/WATCH/ADVANCE.

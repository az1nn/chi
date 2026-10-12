# Feature 002 — Tasks
**State:** Proposed seed; no working audio capture/voice recognition verified.

- [x] VOICE-T001 Document dual-input product intent and V2 art concept (documentation only).
- [x] VOICE-T002 Approve V2.1 dark-first single-surface IME with persistent microphone, preview + Inserir, light alternative; visual-only on 2026-10-10.
- [ ] VOICE-T003 Evaluate offline pt-BR speech recognition candidates, Android permission/IME compatibility, privacy and license.
- [ ] VOICE-T004 Resolve model packaging/download policy (VOICE-OFFLINE-001) and ADR if architectural.
- [ ] VOICE-T005 Add mode switch and mic capture permission UX with explicit start/stop/cancel.
- [ ] VOICE-T006 Build cancel-safe SpeechTranscriber adapter with readiness/error state.
- [ ] VOICE-T007 Add reviewable voice transcript and safe explicit InputConnection commit.
- [ ] VOICE-T008 Test mic denial, sensitive input, editor focus changes, cancellation and accessibility.
- [ ] VOICE-T009 Verify ready-model airplane-mode behavior, no content upload, real-device latency and usability; converge evidence.

**Blocked:** VOICE-T005+ awaits engine/permission/model technical gates. Visual approval is complete, but not an engine approval. Separate Feature 001 unfinished tasks remain unchanged. PR #1 merge requires its own gates.

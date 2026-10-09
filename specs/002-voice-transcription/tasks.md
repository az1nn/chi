# Feature 002 — Tasks
**State:** Proposed seed; no working audio capture/voice recognition verified.

- [x] VOICE-T001 Document dual-input product intent and V2 art concept (documentation only).
- [ ] VOICE-T002 Approve/revise V2 minimal mockup for Escrita/Voz, transcription preview and Inserir.
- [ ] VOICE-T003 Evaluate offline pt-BR speech recognition candidates, Android permission/IME compatibility, privacy and license.
- [ ] VOICE-T004 Resolve model packaging/download policy (VOICE-OFFLINE-001) and ADR if architectural.
- [ ] VOICE-T005 Add mode switch and mic capture permission UX with explicit start/stop/cancel.
- [ ] VOICE-T006 Build cancel-safe SpeechTranscriber adapter with readiness/error state.
- [ ] VOICE-T007 Add reviewable voice transcript and safe explicit InputConnection commit.
- [ ] VOICE-T008 Test mic denial, sensitive input, editor focus changes, cancellation and accessibility.
- [ ] VOICE-T009 Verify ready-model airplane-mode behavior, no content upload, real-device latency and usability; converge evidence.

**Blocked:** T005+ until product/visual and technical engine constraints are approved. Separate Feature 001 unfinished tasks remain unchanged. PR #1 merge requires its own gates.

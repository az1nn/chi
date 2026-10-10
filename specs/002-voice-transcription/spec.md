# Feature 002 — Voice transcription in Chi
**Status:** DRAFT / SPEC SEED — V2.1 visual PASS, recognizer review pending
**Date:** 2026-10-09
**Visual reference:** docs/design/CHI-ART-CONCEPT-001.md v0.2.1 (approved visually)

## Product intent
Chi is a native Android keyboard with two input methods: **Escrita** (stylus handwriting, Feature 001) and **Voz** (speech-to-text, Feature 002). Both prepare text for a focused editor; **Inserir** is an explicit user confirmation. Privacy and offline input remain fundamental.

## User stories
- US-V01: Keep handwriting and voice in one Chi IME panel with a fixed, always-visible microphone. Microphone capture requires explicit user action and permission.
- US-V02: Explicitly start, stop or cancel dictation; permission is requested via Android, not bypassed.
- US-V03: See pt-BR transcription preview and choose Inserir, or discard, without duplicate/stale commits.
- US-V04: With a supported local speech model ready, operate without transmitting dictated audio or text; on unsupported/missing model, surface a safe setup state, not cloud fallback.
- US-V05: Sensitive editors, lost focus, revoked permission, IME closing and mode changes stop capture and invalidate previews.

## Functional requirements
- VR-001: Shared single-panel IME (ink/voice/review states), persistent top-right mic, common focus-safe EditorGateway. Dark default; light alternative approved.
- VR-002: Mic permission + active recording indicator + stop/cancel controls.
- VR-003: SpeechTranscriber interface decoupled from platform or vendor; explicit model readiness/error/cancel state.
- VR-004: Review final transcript before explicit insertion; no insertion during recording.
- VR-005: No audio/text history, recordings, telemetry of contents, or audio uploads by default.
- VR-006: Handle permissions, interruption, accessibility and small screens.
- VR-007: Local inference only; never claim offline readiness without runtime validation.

## Out of scope
Audio-file import, meeting recording, cloud dictation, chat assistant, text prediction, accounts, personal dictionary, themes, multi-speaker diarization.

## Open decisions / gates
- VOICE-OFFLINE-001: model installation approach (bundled versus explicit one-time download); **OFFLINE-001=B remains specific to handwriting**, not silently expanded.
- VOICE-ENGINE-001: evaluate local pt-BR speech SDK/engine, license, microphone lifecycle, package size, performance, Android API support and actual network behavior.
- VOICE-UX-001: V2.1 visual accepted 2026-10-10: mic always visible, explicit Start -> Stop -> Review -> Inserir, no auto-capture. Streaming partial candidates are NOT committed to.
- Do not claim real-world microphone access, offline voice recognition, precision or latency until instrumented evidence.

## Acceptance evidence (proposed)
Native Android device with granted/denied permission, airplane mode when local model is ready, cancellation and focus-race tests, no audio/text transmission and editable transcript inserted exactly once.

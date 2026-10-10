# Feature 002 — Provisional Plan
**Status:** DESIGN-ONLY / NOT ENGINE VALIDATED

## Integration seam
Single-module Kotlin Android app, existing InputMethodService, existing editor safety boundary. Add a single-panel InputModeController for ink/voice state with persistent microphone (no separate pages), VoiceCaptureController for mic permission and lifecycle, SpeechTranscriber interface for asynchronous local recognition, shared CandidatePreview and explicit EditorGateway insertion. Keep stroke state separate from captured audio/transcription.

## Candidate state machine
IDLE -> PERMISSION_REQUIRED -> READY -> RECORDING -> TRANSCRIBING -> REVIEW -> COMMITTED; fail/cancel safely returns to IDLE/READY without text commits. Focus or input-mode change invalidates session tokens. Recording should not begin on tab selection alone.

## Model and privacy
On-device pt-BR speech engine **NOT YET CHOSEN**. Never send dictated content or audio to network. If a model download is needed, request explicit user action before download; clarify lifecycle in VOICE-OFFLINE-001. Avoid storage of audio and transcript except ephemeral active session. Hide recording/preview on sensitive fields.

## Validation
Platform/version/vendor evaluation; Android runtime build and lint; microphone permission/security instrumentation; editor focus/cancellation; Android airplane-mode with verified ready model; no-content-network observability; accessibility and small-screen review; actual device video/screenshots and latency report. No mockup proves these gates.

**Dependencies:** Feature 001 IME foundation and V2.1 visual PASS (2026-10-10); engine, permission and offline technical gates remain. No vendor, technical implementation, or auto-merge authorized.

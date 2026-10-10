# Feature 001 — Handwriting IME (Chi V1)

**Status:** DRAFT — OFFLINE-001 RESOLVED (B); implementation pending | **Created:** 2026-10-09

## Problem
Android users with a stylus need a simple keyboard in which handwriting becomes editable text in other apps, without sending their writing to a server.

## User stories and acceptance

### US1 — Activate Chi as a keyboard (P1)
Given Chi is installed, the user can enable it in system input settings, select it for an editable text field and return to another keyboard. The onboarding must explain that enabling an IME grants access to text the user types.

### US2 — Write and review (P1)
With Chi selected, the user writes one or more strokes in a visible writing region, gets a legible recognition candidate and commits it to the focused editable field. No automatic insertion before recognition has completed or without a defined commit action.

### US3 — Edit without leaving the keyboard (P1)
The user can clear pending ink, choose a candidate, insert a space and delete text via a visible, accessible control. No duplicate commits on rapid taps or input focus changes.

### US4 — Work without network (P1)
With an already downloaded and locally ready language model, recognition and text entry work with airplane mode enabled; no strokes or recognized text are sent to a remote endpoint. On a fresh installation without a model, Chi explains that a one-time, user-initiated model download is needed; it must not silently attempt recognition or upload writing.

### US5 — Graceful unsupported states (P2)
When there is no valid editor, recognition model is missing/failed, pen hardware is absent or input is sensitive, Chi reports a safe state and never crashes or exposes previously entered text.

## Functional requirements
- FR-001: register a functional Android IME service and input-method metadata.
- FR-002: collect stylus stroke coordinates and temporal order; allow clearing ink.
- FR-003: implement an asynchronous `InkRecognizer` contract with candidate results, readiness and recoverable error states.
- FR-004: present candidates and commit chosen text through the current `InputConnection`.
- FR-005: provide space, backspace, keyboard switch and minimal language status.
- FR-006: honor current editor and lifecycle state to cancel stale recognition.
- FR-007: do not transmit or persist user stroke/text content by default.
- FR-008: expose model setup status and never label inference offline until a local-model path is verified.
- FR-009: before the initial model download, show the language, that network access is needed, and request an explicit user action; expose not-installed/downloading/ready/failed states, retry, and safe cancellation.
- FR-010: separate model-file retrieval from ink/text processing; the recognition adapter must perform inference locally after model readiness, with no cloud fallback. On an offline fresh installation, show a non-destructive setup state rather than a recognition result.

## Non-goals (V1)
Predictive typing, autocorrect dictionaries, synchronization, user accounts, cloud recognition, multi-language simultaneous recognition, custom themes, system-wide clipboard management.

## Measurable targets (to validate, not claimed)
- SC-001: 100% of P1 acceptance journeys pass on at least one physical stylus device and one representative emulator where supported.
- SC-002: 100% of sampled recognition requests in airplane mode succeed when the tested model is already locally available, subject to supported inputs.
- SC-003: target p95 below 1s from last pen-up to candidate on test corpus/device, measured and reported; revise target if hardware evidence justifies it.
- SC-004: no raw keystroke, stroke or recognized-text values in logs or network payloads.

## NEEDS CLARIFICATION
- **OFFLINE-001 — DECIDED B (2026-10-09; human approval):** one explicit, user-initiated language-model download is allowed during setup; after successful download, recognition operates offline on-device. Offline recognition on a cold install with no model is **not** required. This decision does not approve any particular recognizer vendor or SDK.
- Platform minimum SDK and stylus-vs-finger acceptance must be set during plan verification; recommended conservative fallback is a keyboard-owned canvas before OS-level stylus handwriting integration.

## Source of truth
This is a product specification, not proof of implementation. OFFLINE-001 is decided; `plan.md` remains provisional until the recognizer's pt-BR support, download UX, Android SDK baseline and exact dependencies are verified.


## Approved interaction baseline (CHI-ART-CONCEPT-001 V2.1, 2026-10-10)
The IME will use a **single panel** for handwriting and speech preview with a **persistent microphone action** at top right (not separate app pages), explicit preview/Inserir, dark-first and an approved optional light mode. VG-01/VG-02 are visual PASS, not native UI or runtime PASS. Feature 002 has separate microphone/engine/offline gates. T008 onboarding can proceed without claiming ink/voice functionality.

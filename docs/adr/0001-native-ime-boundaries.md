# ADR 0001 — Native IME boundaries

**Status:** PROPOSED (IME boundaries); OFFLINE-001 DECIDED B | 2026-10-09

## Context
Chi must accept stylus handwriting in an Android keyboard and insert recognized text into arbitrary compatible editors without cloud dependency for content processing.

## Proposed decision
Use Kotlin native Android `InputMethodService` as the UI/lifecycle boundary, with explicit packages for IME adapter, ink collection, recognition interface/coordinator and editor gateway. Start with one Gradle app module. Separate optional OS stylus handwriting callbacks (API 33+) from the baseline IME-owned drawing surface.

## Consequences
+ Real keyboard integration rather than an isolated handwriting demo.
+ Recognition engine can be replaced without rewriting editor/control logic.
+ Focus-change race handling and privacy can be tested independently.
- Requires device/emulator instrumentation and input-method onboarding.
- Model files may be downloaded once during explicit setup; subsequent inference must run on-device. Size, storage, supported language and recognizer vendor remain to be validated before implementation.

## Decision record — OFFLINE-001 (accepted)
**Chosen: B on 2026-10-09 by explicit human approval.** Chi may download a language model once during onboarding, initiated by the user; once that model is ready, handwriting recognition must function offline. Recognition without a previously downloaded model is not an MVP requirement. No handwriting strokes or recognized text may be uploaded as part of model setup, and there is no cloud inference fallback.

**Trade-off accepted:** first use may require connectivity, storage and a setup step in return for a smaller distributable and access to on-device language models. The app must expose download progress, failure and retry instead of implying recognition is ready.

**Still open:** choose and validate an adapter (Google ML Kit Digital Ink is a candidate only); establish pt-BR support, SDK/size/licensing and instrumented offline/privacy tests. This decision does not ratify the remaining proposed IME architecture.

## References
- https://developer.android.com/reference/android/inputmethodservice/InputMethodService
- https://developers.google.com/ml-kit/vision/digital-ink-recognition/android

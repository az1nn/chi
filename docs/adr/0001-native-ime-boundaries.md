# ADR 0001 — Native IME boundaries

**Status:** PROPOSED | 2026-10-09

## Context
Chi must accept stylus handwriting in an Android keyboard and insert recognized text into arbitrary compatible editors without cloud dependency for content processing.

## Proposed decision
Use Kotlin native Android `InputMethodService` as the UI/lifecycle boundary, with explicit packages for IME adapter, ink collection, recognition interface/coordinator and editor gateway. Start with one Gradle app module. Separate optional OS stylus handwriting callbacks (API 33+) from the baseline IME-owned drawing surface.

## Consequences
+ Real keyboard integration rather than an isolated handwriting demo.
+ Recognition engine can be replaced without rewriting editor/control logic.
+ Focus-change race handling and privacy can be tested independently.
- Requires device/emulator instrumentation and input-method onboarding.
- Model packaging remains unresolved until OFFLINE-001.

## Open choice
**OFFLINE-001** A (offline cold install) vs B (initial download followed by offline use). No recognizer vendor can be accepted until this is resolved.

## References
- https://developer.android.com/reference/android/inputmethodservice/InputMethodService
- https://developers.google.com/ml-kit/vision/digital-ink-recognition/android

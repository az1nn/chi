# Plan — Feature 001 Handwriting IME

**Status:** PROVISIONAL / no runtime implemented | **2026-10-09**

## Proposed architecture (minimal first)

```text
Android keyboard (InputMethodService)
    ├── InkCanvas + input controls
    ├── InkSession (ordered strokes + clear/undo)
    ├── RecognitionCoordinator (cancel/stale-result protection)
    │      └── InkRecognizer interface
    │            └── model-specific adapter (BLOCKED: OFFLINE-001)
    └── EditorGateway (InputConnection; commit / delete / space)
```

Start as one Android app module with package boundaries; avoid modules or dependency injection frameworks without concrete needs.

## Requirements-to-contract map
- US1/FR-001: `InputMethodService`, manifest and settings/IME picker instrumentation.
- US2/FR-002..004: ink stroke data object, recognizer interface, candidate state and explicit commit.
- US3/FR-005..006: editor gateway and focus-safe cancellation.
- US4/FR-007..008: no-content-network design and model-readiness states.
- US5: fail-closed editor/model lifecycle behavior and UI feedback.

## Android and recognition decision
- IME-hosted handwriting canvas is baseline for widest compatibility; OS stylus handwriting callbacks are an optional separate adapter on API 33+.
- Google ML Kit Digital Ink is a leading **candidate**, not yet accepted: language models are downloaded before recognition and inference is then on-device.
- OFFLINE-001=A may require evaluation of redistributable bundled models or a different engine. Do not assert Google ML Kit meets cold-install offline.
- Choose compile/min SDK and exact Gradle/Kotlin dependency versions from current official documentation during implementation; pin them in the actual build, not in this draft.

## Verification strategy
1. Static specifications and skill contract checks via `python3 scripts/verify_repo.py`.
2. Once Android files exist: Gradle lint/test/build on pinned Android toolchain.
3. Emulator/device input-method enable/switch, focus-change and `InputConnection` tests.
4. Pen stroke and recognition tests under airplane mode and absent-model states.
5. p95 latency sample with explicit device/OS, number of strokes, model and test corpus.

## Risks
- R1: first-launch offline model availability (blocking).
- R2: IME accessibility and OEM/Android behavior variance.
- R3: sensitive input privacy/focus races.
- R4: stylus callback support differs from IME-hosted drawing surface.

## Gates
- Human: resolve OFFLINE-001; confirm device baseline if needed.
- Automated: no app build exists yet; CI only validates repository governance.

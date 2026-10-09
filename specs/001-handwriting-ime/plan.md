# Plan — Feature 001 Handwriting IME

**Status:** IMPLEMENTING — T006/T007 Android foundation compiled in CI; runtime/device gates open | **2026-10-09**

## Proposed architecture (minimal first)

```text
Android keyboard (InputMethodService)
    ├── InkCanvas + input controls
    ├── InkSession (ordered strokes + clear/undo)
    ├── RecognitionCoordinator (cancel/stale-result protection)
    │      └── InkRecognizer interface
    │            └── model-specific adapter (selection pending T013)
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
- **OFFLINE-001 = B (approved 2026-10-09):** first-run model download may use network after explicit user action; subsequent recognition must be on-device and usable in airplane mode. Cold-install offline recognition is out of scope.
- Google ML Kit Digital Ink remains a leading **candidate**, not an approved dependency. Official documentation supports `pt-BR` and dynamic model download (about 20 MB per language) with Android library API floor 23. Exact dependency versions, license and runtime evidence are still pending; see `research.md` (T013).
- Define model readiness as `NOT_INSTALLED → DOWNLOADING → READY` with `FAILED`, retry and cancellation paths. The UI must distinguish downloading a language model from sending handwriting, which is prohibited.
- Never silently download, auto-switch to cloud inference, or commit stale candidates on model/focus changes.
- **T006 technical baseline pinned:** JDK 17 / Gradle 8.13 / AGP 8.13.2 / Kotlin 2.3.10 / compileSdk 36 / targetSdk 36 / minSdk 26 / SDK build tools 35.0.0; CI includes Android SDK package installation. This is an implementation baseline, not constitution ratification or an OEM compatibility claim.
- **T007 initial boundary:** single app module, real `InputMethodService` declaration and subtype metadata; initial text controls and basic sensitive editor classification. The placeholder does not capture or recognize ink. Picker is implemented via API-26-compatible `InputMethodManager.showInputMethodPicker()`. Full onboarding/IME instrumentation belongs to T008.

## Verification strategy
1. Static specifications and skill contract checks via `python3 scripts/verify_repo.py`.
2. Once Android files exist: Gradle lint/test/build on pinned Android toolchain.
3. Emulator/device input-method enable/switch, focus-change and `InputConnection` tests.
4. Pen stroke and recognition tests in airplane mode **after a ready local model**, plus cold-install offline behavior (explicit setup state and no inference/network fallback), failed/canceled/retried model download, and privacy/no-stroke-upload evidence.
5. p95 latency sample with explicit device/OS, number of strokes, model and test corpus.

## Risks
- R1: user cannot recognize ink before an initial model download (accepted V1 constraint); setup progress, retry, storage availability and no-network UX require testing.
- R2: IME accessibility and OEM/Android behavior variance.
- R3: sensitive input privacy/focus races.
- R4: stylus callback support differs from IME-hosted drawing surface.

## Gates
- Human: OFFLINE-001 approved as option B on 2026-10-09; preserve any separate architecture/device/PR review or merge gates when they arise.
- Automated: GitHub Actions run `37995370625` on commit `1999d90f` passed `assembleDebug`, `lintDebug` and `testDebugUnitTest`; subsequent documentation/CI artifact commits require exact-HEAD recheck. This is a build and isolated test gate, not emulator/device/airplane-mode proof.

## Visual design reference (PROPOSED)
- Candidate art concept: [CHI-ART-CONCEPT-001](../../docs/design/CHI-ART-CONCEPT-001.md), visual approval pending.
- S01–S05 map to onboarding, IME canvas, candidates/explicit insert, initial model download and sensitive editors.
- Do not treat concept art as Android runtime evidence, an approved visual baseline or a change to OFFLINE-001=B. Implement only after appropriate design gate and device/accessibility validation.

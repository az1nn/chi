# Research — Feature 001 / T013: Digital Ink feasibility

**Status:** EVIDENCE GATHERED; T013 STILL OPEN | 2026-10-09

## Fixed decision
OFFLINE-001 = B: an explicit, user-initiated first-run language-model download is allowed. After model readiness, recognition must work entirely on-device in airplane mode. Raw strokes and recognized text must not be uploaded. No cloud inference fallback.

## Candidate: Google ML Kit Digital Ink Recognition
- The official model catalog lists Portuguese (Brazil), BCP-47 `pt-BR`. The identifier API exposes `PT_BR` and `fromLanguageTag("pt-BR")`.
- The Digital Ink API processes ordered vector strokes (coordinates and timestamps), not camera images or OCR.
- Its Android documentation states a library minimum of API 23. This does not settle Chi's project minSdk.
- Language models can be downloaded with `RemoteModelManager`. Official guidance estimates around 20 MB per language, subject to device and SDK.
- Recognition runs on-device after downloading a model. The download path is distinct from uploading handwriting.
- This is a viable candidate consistent with option B, **not** an approved dependency or runtime-verified result.

## Required before T013 can be checked off
1. Pin the current Android artifact version and verify license/distribution terms.
2. Confirm the exact pt-BR model identifier, download/readiness, progress, failure/retry and storage behavior on device.
3. Demonstrate on-device recognition in airplane mode after model availability, and a safe setup state on a cold install without network.
4. Verify privacy, cancellation/focus changes, candidate quality and performance using Android IME instrumentation.

## Official sources
- https://developers.google.com/ml-kit/vision/digital-ink-recognition/android
- https://developers.google.com/ml-kit/vision/digital-ink-recognition/base-models
- https://developers.google.com/android/reference/com/google/mlkit/vision/digitalink/recognition/DigitalInkRecognitionModelIdentifier
- https://developers.google.com/android/reference/com/google/mlkit/vision/digitalink/recognition/DigitalInkRecognizer

**Evidence limits:** No Android code, APK, Gradle build or device tests exist. T013 stays unchecked.


## Technical validation update — 2026-10-09 (documentation only)

**T013 remains OPEN.** This section narrows the candidate evaluation; it does not approve a vendor, pin a production dependency, claim downloaded-model behavior on a device, or override OFFLINE-001=B.

### Documented Android candidate

- **Official Android guide dependency example:** `com.google.mlkit:digital-ink-recognition:19.0.0` (guide last updated 2026-10-07). This is a **documented evaluation candidate version**, not an adopted Gradle dependency. Confirm its actual resolution, transitive dependencies, current release/repository metadata and reproducibility when a Kotlin/Gradle project exists.
- **SDK prerequisite:** Android API 23+, per the official guide; Chi's app-level `minSdk` has not yet been selected.
- **Model availability:** `DigitalInkRecognitionModelIdentifier.fromLanguageTag("pt-BR")` must be tested for non-null supported resolution at runtime. The API uses `RemoteModelManager.download(model, conditions)`, `isModelDownloaded(model)` and `deleteDownloadedModel(model)`. The guide estimates roughly 20 MB per language; actual storage and download conditions must be measured.
- **Data contract:** capture stroke coordinates with ordering/timestamps; use `Ink`, then `DigitalInkRecognizer.recognize(...)` only after local-model readiness. Official examples log recognized candidate text: **do not copy that logging behavior into Chi**, per Constitution III.

### Privacy, terms and distribution gate

The official *ML Kit Terms & Privacy* (last modified 2025-05-14) says API input and output are processed on-device and not sent to Google's servers. **Separately**, ML Kit may contact Google for models/updates and sends API performance/usage metrics to Google. The terms put user disclosures on the app developer. Therefore, local inference is **not evidence of zero SDK network traffic**, even after a model download.

Before vendor selection: review Google's ML Kit/API terms and app-distribution obligations; document data flows and any SDK metrics, whether they can be controlled, required notices/Google Play Data safety disclosure, and policy compatibility with Constitution III (no content/keystroke telemetry). Do not promise zero network traffic or zero third-party data flow without device/network evidence. Escalate any privacy-policy conflict as a human architecture gate; do not silently dilute the constitution. The ML Kit SDK is governed by the published terms, **not assumed to be open source or Apache-licensed** merely because sample code is available.

### Executable acceptance matrix (pending Android project/device)

| Gate | Required evidence | Status |
| --- | --- | --- |
| Dependency/release | Resolve exact 19.0.0 candidate and transitive artifacts from a reproducible Android build; check terms and notices | PENDING |
| pt-BR | Runtime `pt-BR` identifier and real Brazilian-Portuguese handwriting corpus | PENDING |
| Cold start | Fresh install, model absent, offline: explanatory setup/unavailable UI; no cloud fallback | PENDING |
| Download | User action, consent/UX, readiness query, retry/failure, storage and download conditions | PENDING |
| Airplane mode | Model already READY: recognize strokes with network disabled; no content logging/upload | PENDING |
| Privacy/network | Instrument SDK traffic/metadata separately from content; reconcile disclosures/telemetry policy | PENDING |
| Focus & latency | Cancellation/stale-candidate suppression, sensitive editors, measured pen-up-to-candidate p95 | PENDING |

### Primary references (checked 2026-10-09)

- Android guide (dependency, min API, Ink, RemoteModelManager; updated 2026-10-07): https://developers.google.com/ml-kit/vision/digital-ink-recognition/android
- Model language catalog: https://developers.google.com/ml-kit/vision/digital-ink-recognition/base-models
- Terms & Privacy (metrics, disclosures, inputs on-device): https://developers.google.com/ml-kit/terms
- Android model identifier: https://developers.google.com/android/reference/com/google/mlkit/vision/digitalink/recognition/DigitalInkRecognitionModelIdentifier

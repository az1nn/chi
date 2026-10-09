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

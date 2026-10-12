# CHI-TEST-MATRIX-001 — Chi V1
**Date:** 2026-10-12  | **Workstream:** Feature 001 PR #1  | **Policy:** develop independent work without waiving evidence gates.

Status types: **PASS** = verified evidence; **PENDING** = not yet verified; **BLOCKED** = dependent feature not implemented.

| ID | Test / acceptance | Status | Evidence or required action |
|---|---|---|---|
| CI-01 | Android assembleDebug | PENDING new HEAD | Build PASS in Android job run `38149242750` at `029e2d1`; recheck new T010 HEAD |
| CI-02 | Android lintDebug | PENDING new HEAD | Lint PASS in run `38149242750` at `029e2d1`; recheck new HEAD |
| CI-03 | Android testDebugUnitTest / InkSession deterministic tests | PENDING new HEAD | 7 InkSession unit cases plus 6 new EditorGateway focus/privacy cases; recheck new HEAD |
| CI-04 | Android emulator IME registration/selection/instrumentation | PENDING new HEAD | Run `38149242750` failed after APK install at immediate registry check, before enable/set/instrumentation; bounded wait added, new HEAD required |
| GOV-01 | Spec Kit / governance validation | PENDING new HEAD | Governance PASS at `029e2d1` run `38149242748`; new HEAD required |
| CI-05 | Post-install IME registry discovery within bounded wait; enable/set/instrumentation mandatory | PENDING new HEAD | Previously passed at `f17657f`; failed immediate check at `029e2d1`; 30-second bounded retry added |
| PHY-01 | Install APK and open app on real Android | PASS (older APK) | Operator report on 2026-10-11, prior `c5f1f32` build only |
| PHY-02 | Enable/select Chi as system keyboard | PENDING | Capture device model, Android version and user acceptance |
| PHY-03 | Focus in both Chi setup EditText and external editor; space/delete/switch | PENDING | Device interaction evidence; T008 |
| INK-01 | Stylus down/move/up, multiple strokes, dots, accidental palm input | PENDING | Visual/functional device evidence for T009 |
| INK-02 | Clear ink and confirm no unintended editor text insertion | PENDING | New APK human smoke; T009 |
| INK-03 | Focus switch, IME close, rotation and app switching clear ink | PENDING | Emulator/device evidence; T009/T010 |
| SEC-01 | Password/sensitive editor rejects ink and doesn't reuse previous strokes | PENDING | Privacy acceptance in emulator/device |
| A11Y-01 | Dark UI portrait/landscape/font scale/touch targets | PENDING | VG-03..VG-05 |
| REC-01 | pt-BR recognition, candidate, explicit commit | BLOCKED | T013 recognizer decision then T014 implementation |
| OFF-01 | Model download opt-in, airplane mode recognition, no upload | BLOCKED | T013–T015 and offline evidence |
| PERF-01 | Pen-up to candidate p95 and corpus | BLOCKED | T016 after recognizer integration |
| VOICE-01 | Mic/voice transcription | BLOCKED | Feature 002 voice engine/offline decisions, mic deliberately disabled |

## Manual acceptance checklist (no deadline or gate waiver)
1. **T008:** Activate/select Chi, focus the local test field and an external editor, test space/delete/keyboard switch, inspect a password field.
2. **T009:** Install APK from the new successful CI artifact; draw several stylus strokes, clear, switch fields, close keyboard and confirm no reused ink or automatic text insertion.
3. **VG-03..VG-05:** Capture portrait/landscape/dark/font-scale screenshots and check controls/accessibility.

Do **not** mark T008 or T009 complete on the basis of an app opening alone. Keep CI and physical acceptance distinct. This is a test plan, not proof of execution.

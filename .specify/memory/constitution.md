# Chi Constitution

**Version:** 0.1.0 (proposed) | **Date:** 2026-10-09 | **Status:** draft pending initial architecture review

## I. Native Android, small by default
Chi is an Android native keyboard, Kotlin-first. `InputMethodService` is the product boundary, not a companion demo app. Prefer a single Android app module with clear packages over speculative multi-module architecture. No React Native or backend needed for V1. Introduce libraries only for a documented use case.

## II. Spec before material implementation
Changes affecting user behavior or architecture require a feature `spec.md` with testable outcomes, a technical `plan.md`, and ordered `tasks.md`. Record unresolved material choices under `NEEDS CLARIFICATION` and block their dependent tasks. Use official GitHub Spec Kit commands rather than copying generated agent skills by hand. Changes to architecture require an ADR.

## III. Local-first privacy and editor safety
Writing strokes and recognized text must not be transmitted to a backend or included in logs. No content analytics, cloud learning, clipboard scraping, content history or telemetry of keystrokes in V1. Clearly disclose any first-run model download: downloading a model is distinct from uploading the user's text. Sensitive editors must be handled safely and focus changes must cancel stale recognition.

## IV. Evidence over completion claims
Unit tests, lint, Android build, instrumented IME interaction tests, offline-mode validation, and latency measurements are separate gates. A passing documentation/CI check does **not** demonstrate an APK or a functioning keyboard. Never mask failures, weaken checks, or report unobserved runtime quality as PASS.

## V. Clear ownership and reproducibility
Current Git state and source files outrank handoff and chat. Persist decisions in ADRs and feature artifacts. SIGA orchestrates continuation and concurrency; Android IME owns technical execution. External recognition engines are adapters, not product dependencies hidden throughout the code. No automatic release or merge bypassing pending human gates.

## Open constitutional decision
`OFFLINE-001`: Does Chi need recognition with **zero prior network connection**, including first installation, or is one explicit language-model download acceptable before permanently offline recognition? This affects vendor selection, APK size and first-run UX. Resolve in grilling before choosing the recognition adapter.

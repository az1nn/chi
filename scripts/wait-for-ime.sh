#!/usr/bin/env bash
set -euo pipefail

# IME registry refresh after APK install is asynchronous on some emulator boots.
# Preserve strict failure if the exact service never appears.
ime_id='dev.az1nn.chi/.ime.ChiInputMethodService'
for attempt in {1..15}; do
  if adb shell ime list -a -s | tr -d '\r' | grep -F "$ime_id"; then
    echo "Chi IME registration verified on attempt $attempt."
    exit 0
  fi
  echo "Waiting for Chi IME registration ($attempt/15)."
  sleep 2
done

echo "Chi IME not registered after bounded wait." >&2
adb shell pm path dev.az1nn.chi || true
adb shell ime list -a -s || true
exit 1

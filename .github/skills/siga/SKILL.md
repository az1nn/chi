---
name: siga
description: Chi repository adapter for the canonical SIGA verify-first continuation protocol. Use for standalone "Siga".
---

# SIGA — Chi adapter (NOT a protocol fork)

**Canonical procedure**: https://github.com/az1nn/cpxlabs-admin/blob/master/.agents/skills/siga/SKILL.md

This file only routes SIGA into Chi. Before acting, read the canonical procedure from the linked source. If the source is unavailable, do not invent or rewrite its rules; report the access limit and do only safe read-only reconciliation.

Chi-specific binding:
- Repository: `az1nn/chi`; default base `master` (recheck live).
- Inspect actual branch/HEAD, open PRs/issues, check runs and local dirty files before creating anything.
- Inspect `docs/handoffs/chi-foundation.md`, `.specify/memory/constitution.md`, `specs/` and ADRs.
- Select exactly one `RESUME`, `WATCH` or `ADVANCE`; detect concurrent ownership/drift.
- Delegate Kotlin/IME details to `android-ime` skill; keep ownership of scope, validation, gates and handoff.
- Persist a compact, versioned CAVEMAN handoff; never use chat as canonical project state.
- Never transfer standing auto-merge permissions granted to a **different** repository. Respect Chi's own review and authorization state.

**Invariant**: REAL STATE > HANDOFF > MEMORY > CHAT.

# Chi skill routing

| Skill | Installed | Source / role |
|---|---|---|
| SIGA | Local **adapter** | Canonical procedure in `az1nn/cpxlabs-admin/.agents/skills/siga/SKILL.md`; this repo stores only binding and provenance |
| Android IME | Local **specialist** | Chi-owned Kotlin, handwriting, recognition interface, `InputMethodService`, tests |
| Spec Kit | **Bootstrap pending** | Generate official commands and scripts with `scripts/bootstrap-spec-kit.sh`, do not fork them |
| GODOT | **Not installed** | Present in `az1nn/az1nn/.github/skills`, not applicable to Android-native Chi |

Source inspection: `az1nn/az1nn/.github/skills/README.md`, `siga/SKILL.md`, `godot/SKILL.md`.
Keep skill = behavior; spec/task/ADR/handoff = project data. Specialist returns control to SIGA for gates and handoff.

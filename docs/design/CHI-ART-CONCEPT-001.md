# CHI-ART-CONCEPT-001 — Chi | Escrita natural

**Versão:** 0.1 · **Data:** 2026-10-09 · **Status:** PROPOSED / VISUAL APPROVAL PENDING
**Produto:** teclado Android nativo (Kotlin / InputMethodService), Feature 001.

> North star: “Sua letra. Seu texto.” O Chi deve parecer uma ferramenta silenciosa de escrita: superfície limpa, tinta nítida, transcrição evidente e controle de inserção pelo usuário.

## 1. Princípios e estética
- **Ink-first:** caligrafia é protagonista; cromatismo e molduras não competem com os traços.
- **Nativo e acessível:** estética Material moderna e com personalidade própria; não simular configurações do Android como se fossem do Chi.
- **Controle explícito:** reconhecer e inserir são ações distintas; não fazer auto-commit de resultados provisórios.
- **Privacidade legível:** download inicial de modelo é distinto de transmissão de escrita. Conteúdo não sobe para servidores.
- **Tom:** calmo, preciso e acolhedor. Não usar neon, vidro pesado, gradients marcantes nem metáforas de caderno.

**Brand v0.1 (proposto):** wordmark “chi” em minúsculas, com leve detalhe de traço manuscrito contínuo. Não é um logo aprovado.

## 2. Design tokens

| Token | Light principal | Dark (adaptação requerida) |
| --- | --- | --- |
| Background | #F6F8FB | #0F1521 |
| Surface | #FFFFFF | #1A2330 |
| Primary text | #192334 | #F4F7FC |
| Secondary text | #526071 | #B9C7D8 |
| Border | #DCE3EB | #344356 |
| Ink | #17253C | #EEF3FF |
| Accent/CTA | #4B5DDD | #92A1FF |
| Accent subtle | #E9EDFF | #273464 |
| Success | #127A64 | #71D9B5 |
| Error | #B42332 | #FF8698 |

**Typography:** Roboto / Android system stack, no remote font. Heading 22sp semibold; secondary heading 16sp medium; action labels 14–16sp; meta 12–13sp only when legible.
**Shape:** 20dp radius on writing canvas and cards, 14–16dp on chips/actions, 8dp spacing grid, 16dp outer padding; targets min 48x48dp.
**Visual effects:** quiet hairline borders, mild elevation; no unnecessary illustration in IME.

## 3. Mockup storyboard / telas

### S01 — Onboarding e ativação (Chi Activity)
- Headline: **Escreva à mão. Use em qualquer app.**
- Minimal line art transforms a handwritten stroke into typeset text.
- Two system-mediated steps: **1. Ativar teclado** / **2. Selecionar Chi**.
- Permission disclosure: “Ao ativar um teclado, o Android permite acesso ao texto digitado. Chi não envia sua escrita para servidores.”
- Primary CTA: **Configurar teclado**. Never imply system IME authorization happens inside Chi itself.

### S02 — Captura de escrita (Chi IME, hosted by another app)
- Top of phone belongs to an *illustrative third-party editor*. Only bottom keyboard panel belongs to Chi.
- IME header: discreet “chi”, language status, offline badge only when model is READY.
- Large calm writing canvas with strong dark ink; placeholder “Escreva aqui” only when blank.
- Recognition row: candidates when available; “Reconhecendo…” during work, without fictional success.
- Essential persistent actions: **Limpar / Espaço / Apagar / Teclado**.

### S03 — Revisar e inserir (Chi IME)
- Primary recognized candidate plus up to two alternates in large tactile chips.
- Chip selects candidate; separate primary button **Inserir** commits to editor via active InputConnection.
- If focus or session changes, discard stale candidate; don't insert previously recognized words.
- A valid commit may reset the canvas without celebration or distracting snackbar.

### S04 — Preparar escrita offline (Chi Activity or compact IME state)
- Heading: **Prepare a escrita offline**.
- Copy: “Baixe o modelo de Português (Brasil) uma vez. Depois, o reconhecimento funciona neste aparelho, mesmo sem internet.”
- **Baixar modelo** button appears before network action; no silent downloads.
- NOT_INSTALLED → DOWNLOADING → READY; error offers **Tentar novamente**, secure cancel.
- Progress bar determined only if SDK exposes real measured progress; otherwise use an indeterminate indicator.
- No confusing “backup” or “sync” iconography.

### S05 — Campo sensível (fallback UI)
- Replace ink/candidates with **Escrita desativada neste campo** and a recovery control **Trocar teclado**.
- Do not show a prior session's strokes, transcription or suggestions.

## 4. Keyboard layout (portrait baseline; diagram only)

    +------------------------------------+
    | chi                    pt-BR offline|
    +------------------------------------+
    | candidato 1    candidato 2    ...   |
    +------------------------------------+
    |                                    |
    |       ÁREA DE ESCRITA / INK        |
    |                                    |
    +------------------------------------+
    | Limpar  Espaço  Apagar  Teclado     |
    |                        [Inserir]   |
    +------------------------------------+

**Sizing target:** approximately 300–350dp tall on a reference portrait device, dynamically constrained by window insets, system bar, viewport and font scaling. This is NOT a hard-coded keyboard height. In landscape/small screens prioritise useful canvas area and essential controls.

## 5. Asset and component inventory

| ID | Name | Delivery target | Status |
| --- | --- | --- | --- |
| CHI-A01 | Monoline “chi” wordmark | Vector, dark/light variants | PROPOSED |
| CHI-A02 | Android adaptive app/IME icon | Foreground/background assets | PROPOSED |
| CHI-A03 | Canvas, hint and border | Native custom View | PROPOSED |
| CHI-A04 | Realtime ink stroke renderer | Native Path/Canvas | PROPOSED |
| CHI-A05 | Candidate chip, selected/normal | Native component | PROPOSED |
| CHI-A06 | Insert/clear/space/delete/switch controls | Native components | PROPOSED |
| CHI-A07 | Model/offline/language/security indicators | Text + vectors | PROPOSED |
| CHI-A08 | Minimal action icons 24dp | Material-like vectors | PROPOSED |
| CHI-A09 | Onboarding ink-to-text illustration | Optional vector | PROPOSED |
| CHI-A10 | Download/failed/ready/locked states | Native components | PROPOSED |

No raster sprite sheets for control elements. Live stroke rendering must use ink paths, not a mockup background. The generated concept image is illustrative and must not be confused with a production screenshot.

## 6. Accessibility and microinteractions
- Pen-first. Finger drawing is not silently considered approved for V1.
- WCAG AA text contrast target >=4.5:1; measure actual UI colors, including on disabled states. Never convey state only with color.
- Hit areas >=48dp; clear TalkBack actions “Limpar escrita”, “Inserir candidato”, “Apagar caractere”, “Trocar teclado”.
- Android large-font scaling, small displays, screen rotation, navigation insets and reduced-motion settings are review cases.
- Avoid keyboard-only gestures that cannot be discovered or used with accessibility services.
- Announce candidate-ready/status changes without noisy repeated announcements; optional discreet haptics.
- Sensitive editor types: stop ink capture, hide prior state and avoid content persistence/logging/telemetry.
- Never send ink, recognized text or raw editor data to a backend; recognizer choice remains unapproved.

## 7. State / acceptance matrix

| Gate | Evidence | Status |
| --- | --- | --- |
| VG-01 | Composition, palette, typography, wordmark accepted | PENDING HUMAN |
| VG-02 | S01–S05 flows and separate “Inserir” action accepted | PENDING HUMAN |
| VG-03 | Portrait, landscape, dark mode and font scaling previews | NOT TESTED |
| VG-04 | Measured contrast, TalkBack and touch target tests | NOT TESTED |
| VG-05 | APK screenshot and ink-to-editor test on device | NOT IMPLEMENTATION PROOF |

## 8. Spec Kit continuity
- Source of truth: specs/001-handwriting-ime/spec.md (US1–US5; FR-001..010).
- Implementation plan: specs/001-handwriting-ime/plan.md and tasks.md. This design informs T008 (activation), T009 (canvas), T011 (candidate/actions), T013–T014 (model states), T017–T018 (UI/device/a11y verification).
- **OFFLINE-001=B preserved:** explicit user-initiated model download, then inference on device only; no cloud fallback.
- **No changes authorized** to recognizer vendor, SDK/licensing, hardware support claims, pending tests or merge gates.
- After design approval: implement native design tokens/components behind Feature 001 tasks, capture real runtime visual evidence and assess divergences. Keep PR #1 draft until distinct engineering/review gates pass.

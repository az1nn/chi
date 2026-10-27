# CHI-ART-CONCEPT-001 — Dual Input (V2.1)
**Versão:** 0.2.1 · **Data:** 2026-10-10 · **Status:** APPROVED VISUAL (VG-01/VG-02 PASS), RUNTIME PENDING
**Histórico:** V1 (prancha urbana/dark complexa) = **REJECTED** em 2026-10-09. NÃO utilizar V1 como referência aprovada, nem herdar dicionário pessoal, temas, chat de exemplo, logo ilustrado, teclado QWERTY ou adornos.

## 1. Intenção do produto
Chi é **um teclado Android nativo para converter fala e escrita manual em texto editável no aplicativo atual**, com o mínimo de interface possível. Dois modos de entrada com a mesma saída: **Escrita** e **Voz**. O conteúdo é revisado e então enviado ao campo ativo apenas mediante ação explícita **Inserir**.

North star: **“Escreva ou fale. Vira texto.”**

### Fora da direção visual V1
Não apresentar chats de terceiros como parte do Chi, dicionário, perfis, login, feed, atalhos, temas customizados, transcrição de arquivos, assistente conversacional, IA generativa, corretor gramatical, efeitos 3D, paisagens, decoração urbana ou calendário. Configurações somente quando estritamente necessárias para ativar a IME, conceder microfone e preparar modelos.

> **Registro histórico:** as seções 2–7 descrevem a proposta V2 superada. Em qualquer conflito, a **seção 8 — baseline V2.1 aprovado — prevalece**: uma única superfície IME para escrita e voz, microfone fixo no canto superior direito, dark mode padrão, light mode alternativo aprovado. Não implementar navegação em abas separadas com base nos exemplos antigos.

## 2. Conceito visual
- **Minimalismo funcional, light-first:** fundo off-white, tinta grafite, uma única cor de ação azul-índigo. Muito espaço de escrita e poucos controles.
- **No máximo dois elementos de navegação primária:** alternador segmentado **Escrita | Voz**.
- **Hierarquia:** (1) área de entrada, (2) texto transcrito para revisar, (3) ação Inserir.
- **Sem navbar de aplicativo dentro da IME:** a interface própria do Chi é só a área inferior do Android. Área de texto do aplicativo hospedeiro acima serve apenas para contextualização.
- **Marca:** apenas palavra **chi**, discreta em tipografia sem serifa; sem logo chamativo. Nenhum wordmark do conceito rejeitado é aceito por herança.
- **Fonte:** Roboto/system sans. Títulos e controles curtos, sem tipografia decorativa.
- **Cores propostas:** background #F7F8FA; canvas/surface #FFFFFF; texto #17212D; secundário #687582; borda #E2E6EC; action #365BE8; action pale #EDF1FF; ativo/recording #D94949; success #237B62.
- **Dark:** adaptação futura para contraste/acessibilidade; não representar modo escuro como tema oficial aprovado.
- **Tokens:** 8dp spacing base, 14–16dp gutters, 14–18dp radius suave, controles >=48dp, strokes de tinta com alta legibilidade.

## 3. Storyboard de mockups (V2)
### S01 — Escrita (modo padrão)
Área inferior de IME com cabeçalho compacto [chi] [Escrita | Voz]. Canvas branco de maior área com um traço manuscrito de exemplo **“bom dia”**. Linha de preview **Texto: bom dia**, separada da escrita; pode exibir carregamento antes de reconhecer. Ações de rodapé: **Limpar**, **Apagar** (quando aplicável) e botão principal **Inserir**. Ícone/título de microfone não precisa aparecer além da aba Voz.

### S02 — Voz (captura)
Mesmo cabeçalho, aba **Voz** selecionada. Em vez de canvas, um ícone simples de microfone e o estado inequívoco **Ouvindo…**. Indicação de nível simples, discreta e abstrata (não fingir onda real). Botão **Parar** claro; sem captar áudio ao apenas trocar de aba. Microfone somente após gesto explícito **Iniciar gravação** e consentimento Android. O áudio jamais deve ser gravado como histórico local por padrão.

### S03 — Voz (revisão)
Ao parar, exibir **Transcrição:** seguido de texto reconhecido demonstrativo **“Podemos conversar amanhã?”**, como prévia editável/revisável se a implementação permitir; botão **Inserir** somente quando texto pronto e editor ativo. Botão de regravar/limpar separado. Não afirmar streaming, latência, precisão, pontuação ou offline sem testes.

### S04 — Setup necessário (mínimo)
Quando permissão de microfone falta: texto **Permitir microfone** e botão que desencadeia a permissão do Android; sem atalho silencioso. Quando um modelo local falta: **Preparar modo offline** + botão **Baixar modelo** explícito, por idioma/modalidade; não chamar modo offline de pronto antes de READY.

### S05 — Campo sensível / perda de foco
Canvas e transcrição vazios/ocultos; texto **Entrada desativada neste campo**; nunca reaproveitar resultado de outra sessão ou editor. Trocar teclado quando necessário.

## 4. Layout IME esquemático

    +------------------------------------+
    | chi            [Escrita] [Voz]     |
    +------------------------------------+
    |                                    |
    |     área de tinta OU microfone     |
    |                                    |
    +------------------------------------+
    | prévia do texto reconhecido        |
    +------------------------------------+
    | Limpar                    [Inserir] |
    +------------------------------------+

- Modo entrada usa **um único container**: Canvas para caneta OU componente de áudio; nunca telas e menus completos.
- Para voz, o botão principal é **Iniciar**, depois **Parar**, depois **Inserir**, nunca iniciar automaticamente.
- Para escrita, o traço pode ser reconhecido de forma assíncrona, mas o commit permanece explícito e focus-safe.
- A altura não é fixa; preservar espaço para o app hospedeiro com IME/window insets, telas pequenas, landscape e fonte aumentada.

## 5. Componentes necessários
| ID | Componente | Entrega alvo | Estado |
| --- | --- | --- | --- |
| CHI-UI-01 | Header chi + segmented control (Escrita/Voz) | Android nativo | PROPOSED |
| CHI-UI-02 | Ink canvas + stroke path | Android custom View | PROPOSED |
| CHI-UI-03 | Microfone / estados Iniciar, Ouvindo, Parar | Android native views | PROPOSED |
| CHI-UI-04 | Prévia de texto/candidato de transcrição | Android native views | PROPOSED |
| CHI-UI-05 | CTA explícito Inserir + Limpar/Refazer | Android native views | PROPOSED |
| CHI-UI-06 | Prompt de permissão de microfone, estado de modelo | Android Activity/IME + system dialog | PROPOSED |
| CHI-UI-07 | Segurança/focus/sensitive disabled | Android native states | PROPOSED |

Sem imagens rasterizadas para componentes funcionais; o mockup é **conceitual**, não screenshot de build.

## 6. Privacidade, arquitetura e pendências
- **Feature 001 / handwriting:** preservar OFFLINE-001=B (download inicial explícito de modelo de tinta; inferência local depois; nenhum fallback cloud). Reconhecedor ainda não selecionado.
- **Feature 002 / voz:** proposta de transcrição **on-device**, sem rede para áudio/conteúdo, no mesmo editor via InputConnection, porém engine, suporte pt-BR, modelo, requisitos de rede para setup, permissões, gravação temporária e suporte Android **dependem de validação de engenharia**. Não presumir que a mesma engine resolve voz e escrita.
- Captura de microfone exige ação explícita e permissão; foco/sensitive/input type deve impedir captura ou commit indevido.
- Nenhum texto, áudio ou stroke deve ir para analytics, logs ou backend.
- A fonte da proposta de voz está em `specs/002-voice-transcription/`. A V1 do handwriting não é automaticamente considerada entregue.

## 7. Gates de aceite históricos da V2 (estado vigente na seção 8)
- **VG-01 PENDING HUMAN:** mockup V2 minimalista, light-first, **Escrita | Voz** e prévia + Inserir.
- **VG-02 PENDING HUMAN:** sem menu, dicionário, tema, chat ou personalizações; estados S01–S05 coerentes.
- **VG-03 NOT VERIFIED:** tela pequena/landscape/dark/large fonts/accessibility.
- **VG-04 NOT VERIFIED:** permissão mic, privacidade, readiness de modelo e offline de voz (testar separadamente).
- **VG-05 NOT VERIFIED:** APK/testes de dispositivo/latência/reconhecimento real.
- Merge do PR #1 não autorizado por este documento. Manter status de proposta até aprovação humana da nova imagem.

## 8. Aprovação humana — baseline visual V2.1 (2026-10-10)

**APPROVED — VISUAL ONLY.** Aprovada a última prancha de Chi com três estados do MESMO teclado Android: escrita manuscrita, captura de voz e prévia de texto. A composição e o modo escuro passam a ser baseline visual; o **light mode V2 anterior permanece aprovado como alternativa**. A prancha V1 urbana permanece REJECTED.

### Decisões vinculantes de UI
1. **Superfície única:** escrita à mão e voz convivem no mesmo painel IME, sem navegar para outra página nem alternar entre Activities para essas tarefas.
2. **Microfone fixo e sempre visível:** botão circular na posição superior direita da barra do Chi, inclusive enquanto a caneta está ativa e durante revisão; tocar inicia a sequência explícita de captura/permissão, nunca gravação automática ao abrir a IME.
3. **Dark mode padrão:** fundo grafite profundo, canvas escuro, traços claros, azul apenas para ação principal e microfone. Light mode aprovado, disponível como modo secundário.
4. **Revisão e commit:** resultado de escrita ou voz é mostrado no mesmo painel; botão Inserir realiza commit explícito ao editor ativo, protegido contra foco obsoleto e campos sensíveis.
5. **Layout essencial:** header Chi + Limpar + mic fixo; canvas/estado de áudio/resultado; campo de prévia; rodapé compacto com Inserir e alternância de teclado quando necessária.
6. **Sem scope creep:** não incluir dicionário, histórico, assistente, feed, telas de chat próprias, customização extensa, recursos de áudio não especificados ou upload de conteúdo.
7. **Privacidade/offline:** preservado OFFLINE-001=B para escrita; motor de voz e offline de voz ainda dependem de seleção e testes próprios. A frase “funciona sem internet” na arte é uma intenção, não prova de runtime.

### Aprovação e gates
- **VG-01: PASS (human visual)** — composição dark-first e identidade minimalista.
- **VG-02: PASS (human visual)** — escrita + voz na mesma IME, microfone persistente, prévia e Inserir.
- **VG-03: PENDING TECHNICAL** — adaptação de telas, light/dark, acessibilidade, contraste, tamanho de toque e IME insets.
- **VG-04: PENDING RUNTIME** — permissão de microfone, recognizer pt-BR, privacidade/offline e cancelamento seguro.
- **VG-05: PENDING DEVICE** — build, screenshots reais, reconhecimento, latência e testes físicos.

**Evidência de aceite:** aprovação explícita do usuário no chat após a prancha V2.1 (2026-10-10). Mockup aprovado não equivale a APK funcional, nem autoriza merge do PR #1.

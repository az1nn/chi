# Chi

**Chi** é um projeto Android nativo em **Kotlin**: um teclado (IME) minimalista para escrever com caneta e converter traços em texto, com prioridade para processamento no dispositivo.

> Estado: **fundação Kotlin/Android compilada e IME experimental**. O build debug e os testes unitários passaram em CI; escrita à mão e reconhecimento ainda **não** foram implementados. **OFFLINE-001=B** aprovado: um download inicial e explícito do modelo é permitido; depois o reconhecimento deverá operar offline. O motor ainda não foi escolhido.

## Norte do produto

- Digitar em qualquer campo Android compatível por meio de um `InputMethodService`.
- Capturar escrita à mão, sugerir transcrição e permitir correção antes de inserir no editor.
- Começar pequeno: pt-BR, escrita por caneta, área de escrita, candidato, espaço e apagar.
- Privacidade por padrão: traços e texto não enviados a servidores, sem histórico de conteúdo por padrão.

## Operação

- `Siga`: retoma via adaptador SIGA e estado persistido em Git.
- `Android IME`: carrega a skill especialista em `.github/skills/android-ime/SKILL.md`.
- `.specify/memory/constitution.md`: regras do projeto.
- `specs/001-handwriting-ime/`: spec, plano preliminar e tarefas ordenadas.
- `docs/handoffs/chi-foundation.md`: handoff operacional.
- `python3 scripts/verify_repo.py`: valida a fundação documental.

## Desenvolvimento Android — Feature 001

- **Toolchain auditado:** JDK 17, Gradle 8.13, AGP 8.13.2, Kotlin Gradle Plugin 2.3.10, compileSdk 36, targetSdk 36, minSdk 26.
- **Módulo:** `app/`; serviço registrado em `app/src/main/AndroidManifest.xml` e `res/xml/method.xml`.
- **Estado do teclado:** serviço IME inicial com botões de espaço, apagar e seletor de teclado. Não captura escrita, não transcreve, não solicita rede e não integra SDK de terceiros. Campos de senha recebem aviso de proteção.
- **Validação:** GitHub Actions `Chi Android (build, lint, unit)` executa `assembleDebug`, `lintDebug` e `testDebugUnitTest`. Ver CI da PR #1; o teste em dispositivo e a UX de ativação continuam T008.
- **Local:** instale Android SDK Platform 36, Build Tools 35.0.0, JDK 17 e Gradle 8.13; execute `gradle --no-daemon :app:assembleDebug :app:lintDebug :app:testDebugUnitTest`. O Gradle Wrapper ainda não foi versionado.
- **Privacidade:** manifesto sem permissão `INTERNET`; não confundir isso com auditoria de rede de um reconhecedor futuro.

## GitHub Spec Kit

O fluxo é **constitution → specify → clarify (quando necessário) → plan → tasks → analyze → implement → converge**. Não executar `implement` enquanto houver decisões bloqueantes.

O GitHub Spec Kit oficial **0.12.11 já foi instalado** no PR #1, via checkout isolado do GitHub Actions e commit `6319c7a`. A integração Copilot usa skills em `.github/skills/speckit-*`; os templates, scripts e manifests oficiais ficam em `.specify/`. A integração do Spec Kit é oficial e versionada. Posteriormente, T006–T007 adicionaram um módulo Kotlin/Android compilável e um IME experimental; isso **não** comprova ativação em dispositivo, captura de tinta ou reconhecimento.

Para usar o CLI no checkout local (a inicialização não deve ser repetida sem necessidade):

```bash
uv tool install specify-cli==0.12.11
git switch feat/001-chi-skills-speckit
specify version
python3 scripts/verify_repo.py
```

A integração já está versionada. O script `scripts/bootstrap-spec-kit.sh` é uma recuperação idempotente: detecta `.specify/integration.json` e evita inicialização repetida. A constituição do Chi, o SIGA local e o especialista Android IME não foram sobrescritos. Use `/speckit-specify`, `/speckit-plan`, `/speckit-tasks` e `/speckit-analyze` no agente compatível, mantendo os gates de implementação.

Referências: https://github.com/github/spec-kit e https://docs.github.com/en/copilot/how-tos/copilot-on-github/customize-copilot/customize-cloud-agent/add-skills

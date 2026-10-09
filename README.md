# Chi

**Chi** é um projeto Android nativo em **Kotlin**: um teclado (IME) minimalista para escrever com caneta e converter traços em texto, com prioridade para processamento no dispositivo.

> Estado: **foundation/spec only**. Ainda não existe APK, código Android ou reconhecimento funcional. **OFFLINE-001=B** aprovado: um download inicial e explícito do modelo é permitido; depois o reconhecimento deverá operar offline. O motor ainda não foi escolhido.

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

## GitHub Spec Kit

O fluxo é **constitution → specify → clarify (quando necessário) → plan → tasks → analyze → implement → converge**. Não executar `implement` enquanto houver decisões bloqueantes.

O catálogo oficial de templates, comandos, scripts e integração Copilot deve ser instalado **no checkout local**, não reproduzido manualmente neste repositório:

```bash
uv tool install specify-cli
git switch feat/001-chi-skills-speckit
bash scripts/bootstrap-spec-kit.sh
specify version
python3 scripts/verify_repo.py
```

O script preserva a constituição do Chi, exige checkout limpo e utiliza `specify init --here --force --integration copilot --ignore-agent-tools`. Revisar e versionar os arquivos gerados em um commit separado.

Referências: https://github.com/github/spec-kit e https://docs.github.com/en/copilot/how-tos/copilot-on-github/customize-copilot/customize-cloud-agent/add-skills

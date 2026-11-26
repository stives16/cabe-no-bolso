# Cabe no bolso — Planejador de despesas compartilhadas

Aplicação desktop em Java Swing para cadastrar participantes, controlar despesas compartilhadas, calcular rateios e acompanhar limites individuais.

## Requisitos
- JDK 17 ou superior (não utiliza bibliotecas externas).
- Visual Studio Code com Extension Pack for Java, ou outra IDE Java.

## Como executar no VS Code
1. Extraia a pasta do projeto.
2. Abra a pasta `cabe_no_bolso` no VS Code (Arquivo > Abrir Pasta).
3. Abra `src/Main.java`.
4. Clique em **Run** acima do método `main`.

Também é possível compilar pelo terminal, dentro da pasta do projeto:

```bash
javac -encoding UTF-8 -d out src/Main.java src/model/*.java src/controller/*.java src/view/*.java
java -cp out Main
```

## Funcionalidades
- Cadastro, edição e exclusão de participantes (não permite excluir quem possui despesa vinculada).
- Cadastro, edição e exclusão de despesas.
- Despesa fixa rateada entre os participantes escolhidos, com distribuição dos centavos restantes em ordem crescente dos códigos.
- Despesa por pessoa, cobrando o valor informado de cada participante selecionado.
- Resumo com despesas, custo total, total individual e situação do orçamento.
- Salvamento e recuperação por serialização no arquivo local `planejamento.dat`.
- Validações de campos, valores, códigos, participantes repetidos e vínculos.

## Estrutura
- `model`: entidades, regras de cálculo e persistência.
- `controller`: coordenação das operações.
- `view`: interface gráfica Swing.
- `docs`: diagrama UML e roteiro para o relatório.

## Como reproduzir o caso de referência
Cadastre os participantes: 1 Ana (70,00), 2 Bruno (60,00), 3 Carla (70,00).
Cadastre as despesas:
1. Transporte — Fixa — 90,00 — códigos 1,2,3.
2. Almoço — Por pessoa — 20,00 — códigos 1,2,3.
3. Atividade recreativa — Fixa — 30,00 — códigos 1,3.
4. Sobremesa — Por pessoa — 10,00 — códigos 1,2,3.

Resultado esperado: Ana R$ 75,00 (excede em R$ 5,00); Bruno R$ 60,00 (dentro do orçamento); Carla R$ 75,00 (excede em R$ 5,00); custo total R$ 210,00.

## Observação acadêmica
Revise, execute e compreenda o código antes da entrega. O enunciado exige que os integrantes assumam a condução do projeto e registrem suas contribuições reais. O relatório técnico em PDF e o repositório GitHub também são entregas obrigatórias.

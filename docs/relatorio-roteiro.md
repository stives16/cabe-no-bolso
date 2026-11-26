# Cabe no bolso — Planejador de despesas compartilhadas

> Completem os campos entre colchetes com informações verdadeiras da equipe e descrevam apenas funcionalidades efetivamente implementadas e testadas.

## 1. Apresentação
**Integrantes:** [nomes completos e matrículas]  
**GitHub:** [link do repositório]  
**Objetivo:** desenvolver uma aplicação Java para organizar despesas compartilhadas, calcular contribuições individuais e comparar os totais com os orçamentos.

## 2. Modelagem e organização
A classe `Planejamento` mantém participantes em um `TreeMap`, que organiza os códigos e impede duplicidade, e despesas em uma `ArrayList`, que preserva a ordem de cadastro. `Participante` protege seus atributos por encapsulamento. `Despesa` é abstrata e possui duas especializações: `DespesaFixa` e `DespesaPorPessoa`, que sobrescrevem `calcularCota`. O uso de referências do tipo `Despesa` permite polimorfismo. A interface `Persistencia` define salvar e carregar, implementada por `ArquivoPersistencia`.

A aplicação separa modelo, visão e controlador nos pacotes `model`, `view` e `controller`.

## 3. Implementação
Descrever telas, eventos, cadastro, cálculo de rateio, validações, resumo e persistência que foram efetivamente concluídos. Informar versão do JDK.

## 4. Testes e resultados
Registrar entrada, esperado, obtido e evidência (captura de tela). Executar obrigatoriamente:
- Caso principal do enunciado: Ana 75,00; Bruno 60,00; Carla 75,00; total 210,00.
- Rateio fixo de 100,00 entre códigos 1, 2 e 3: 33,34; 33,33; 33,33.
- Despesa sem participantes e valor inválido: deve recusar sem alterar dados.
- Orçamento de Ana alterado para 80,00: total permanece 75,00 e situação fica dentro do limite.
- Excluir atividade recreativa do caso principal: total individual de cada pessoa 60,00 e custo total 180,00.
- Salvar e reiniciar; carregar sem duplicar registros.
- Arquivo ausente/inválido: mostrar mensagem e preservar os dados em uso.

## 5. Avaliação e melhorias
Discutir encapsulamento, herança, polimorfismo, MVC, precisão dos centavos, consistência e dificuldades reais encontradas. Listar limitações e melhorias.

## 6. Contribuições
| Integrante | Implementação/integração | Validação | Documentação |
|---|---|---|---|
| [Nome] | [contribuição real] | [testes] | [parte] |

## 7. Referências
[Material da disciplina e fontes externas realmente consultadas, conforme ABNT.]

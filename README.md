# PurityTrackerTool

A PurityTrackerTool é uma ferramenta criada para identificar e rastrear refatoramentos do tipo "puro" e "floss". 

Este documento tem como objetivo orientar você na configuração e execução da PurityTrackerTool.

## Pré-requisitos

Para o funcionamento adequado da PurityTrackerTool, você precisa:

1. Possuir um projeto em Java.
2. O projeto deve ser capaz de ser compilado sem erros.

## Utilizando a PurityTrackerTool

Segue abaixo o passo a passo para a execução correta da ferramenta:

### Clonagem dos Repositórios

1. *Clone a versão original do repositório:* Refere-se ao código antes do processo de refatoramento. Quando realizar a clonagem do repositório, é importante capturar o hash do commit relativo a essa versão, que chamaremos de `hash01`.
2. *Clone a versão de destino do repositório:* Refere-se ao código após o processo de refatoramento. Similar ao passo anterior, após a clonagem do repositório, capture o hash do commit dessa versão, que chamaremos de `hash02`.

### Executando a Ferramenta


1. *Inicie a PurityTrackerTool:* Após abrir a ferramenta, navegue até a classe `mainFluxograma02`.
2. *Execute a classe `mainFluxograma02`:* Esta classe deve ser executada passando como parâmetros o caminho (path) do local em que as versões original e de destino foram clonadas. Além disso, é necessário fornecer a URL do projeto no Git, bem como os hashes `hash01` e `hash02`.

Com a execução destes passos, a PurityTrackerTool estará pronta para uso. Caso encontre quaisquer dificuldades, não hesite em buscar por suporte.

# (Toy model) Gerenciador de Smart House

Esse projeto é o trabalho final da cadeira Programação Orientada a Objetos, em 2022/2.
O grupo que fez este trabalho são os alunos Matheus B. Tissot, Eduardo Viegas e Alvaro Guglielmin.

Esse é meu primeiro projeto fazendo algo que não é um exercício básico proposto para fixação de conteúdos.

## Descrição do projeto

Os requisitos do projeto eram:
1) 8 Classes totais
2) 1 interface
3) 1 classe abstrata
1) Capacidade de cadastrar comodos
2) Capacidade de associar dispositivos a comodos
3) Capacidade de editar o estado dos dispositivos
4) Capacidade de listar todos os comodos e dispositivos, e o estado dos dispositivos
5) Capacidade de salvar o estado atual da 'casa' num arquivo.

## Funcionalidades presentes
1) Todos os requisitos de estrutura do projeto (8 classes, 1 interface, 1 abstrata)
2) Adicionar comodos, e escolher qual comodo visualizar, utilizando um menu dropdown
3) Adicionar dispositivos a comodos, que ficam visiveis quando estamos no comodo escolhido
4) Remover dispositivos (pensando numa casa real, foi escolhido a impossibilidade de se remover comodos, somente de dispositivos)
5) Editar dispositivos. Diferentes tipos de dispositivo possuem opções diferentes de edição. (E dispositivos com Timer possuem um campo para inserir o tempo que o timer irá iniciar)
6) Listar o estado atual da casa, listando os comodos, e os dispositivos dentro dos comodos, num formato de árvore.
7) Salvar o estado atual da casa num arquivo. O usuário recebe um prompt para inserir o nome do arquivo. Se não inserir nada, será salvo em "casa.txt".
8) Opção de testar (inclusive com o atalho CTRL+T) as funcionalidades do programa, no menu superior. Essa funcionalidade irá criar dois comodos, uma "Sala" e uma "Cozinha", e adicionar 3 dispositivos à Sala, e um dispositivo à Cozinha.
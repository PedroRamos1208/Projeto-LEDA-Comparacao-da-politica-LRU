Plano de Experimento – LRU com Árvore Splay vs LinkedList + HashMap
 22, Agosto, 2025


1. Introdução
A eficiência de algoritmos de substituição em caches é um tema central em estruturas de dados e sistemas. O Least Recently Used (LRU) é um dos algoritmos mais utilizados por seu bom equilíbrio entre simplicidade e desempenho. Este experimento tem como objetivo comparar o comportamento do LRU implementado em duas abordagens distintas:
* Árvore Splay (estrutura dinâmica, com operações de acesso adaptativas).
* LinkedList + HashMap (estrutura híbrida que combina lista duplamente ligada para manter a ordem dos elementos e HashMap para permitir acesso direto em O(1)).


2. Motivação	
Sistemas modernos, como bancos de dados, navegadores e servidores de aplicações, dependem fortemente de mecanismos de cache para garantir desempenho e responsividade. A escolha da política de substituição e da estrutura de dados subjacente influencia diretamente na eficiência da cache, principalmente em cenários de grande volume de acessos.
A política Least Recently Used (LRU) é amplamente utilizada para refletir o princípio de localidade temporal: elementos acessados recentemente tendem a ser reutilizados em breve. Contudo, a forma como a LRU é implementada impacta significativamente o desempenho:
Implementações baseadas em LinkedList + HashMap oferecem operações O(1) na inserção, busca e remoção, combinando simplicidade e eficiência.
Estruturas como a Árvore Splay podem explorar padrões de acesso com localidade, ajustando-se dinamicamente e oferecendo bom desempenho mesmo em cenários com mudanças no padrão de acessos.
Assim, investigar o desempenho dessas duas abordagens sob diferentes cargas e padrões de acesso é essencial para fundamentar decisões sobre implementações eficientes de caches.


3. Objetivo	
O objetivo deste experimento é comparar o desempenho da política LRU implementada com Árvore Splay e com LinkedList + HashMap.
A pesquisa busca responder em quais cenários cada abordagem apresenta melhor desempenho em termos de tempo de execução, eficiência e escalabilidade sob diferentes cargas de acesso. Para isso, serão executados testes com cargas de tamanhos variados (pequenas, médias e grandes) e com diferentes padrões de acesso (aleatório, com localidade e sequencial/scan).
Os resultados serão analisados estatisticamente, permitindo identificar vantagens, limitações e trade-offs de cada abordagem.


4. Questões de pesquisa
Questão Geral:
Como diferentes estruturas de dados (Árvore Splay e LinkedList + HashMap) impactam o desempenho da política de substituição LRU quando submetidas a diferentes níveis de carga e padrões de acesso?

Questão Específica:
Como a variação da carga (pequena, média e grande) e dos padrões de acesso (uniforme, sequencial e Zipf) influencia o desempenho e a corretude da política LRU implementada em Árvore Splay e em LinkedList + HashMap? Até que ponto é possível modelar o comportamento e a escalabilidade de ambas as implementações, identificando o ponto de degradação de desempenho em cenários de uso intensivo?


5. Metodologia

5.1 Preparação do ambiente
Linguagem/compilação: Java.
Hardware/OS: descrever CPU, RAM, SO; fechar apps; modo energia “alto desempenho”.
JVM: desativar variações de frequência se possível.
Fixar aleatoriedade: usar carga explícita em todo gerador para reprodutibilidade.

5.2 Estruturas de dados
Splay Tree: árvore binária de busca auto ajustável, garantindo que elementos recentemente acessados fiquem próximos da raiz, reduzindo custo em padrões com localidade.
LinkedList + HashMap: lista duplamente ligada para manter a ordem de uso dos elementos e HashMap para permitir acesso, inserção e remoção em O(1), sendo a abordagem mais utilizada em implementações de LRU em bibliotecas padrão.

5.3 Cargas
Três níveis de carga (número de operações), divididos em três partes para visualização em gráficos:
Pequena: 1.000 – 100.000
Média: 100.000 – 1.000.000
Grande: 1.000.000 – 5.000.000

5.4 Padrões de acesso
Uniforme: todos os elementos têm a mesma probabilidade de serem acessados.
Scan sequencial: sequência percorrida uma única vez (cenário hostil ao LRU).
Zipf: distribuição de popularidade, onde poucos elementos concentram a maior parte dos acessos.

5.5 Tamanho do Cache	
O cache terá tamanho equivalente a 10% da carga usada, garantindo que mais elementos que a capacidade do cache sejam inseridos, forçando substituições LRU.

5.6 Testes realizados 
Cada operação será executada 15 vezes, considerando a média de tempo:
Inserção simples em cache não cheio.
Inserção em cache cheio (com remoção).
Inserção de elementos repetidos.
Inserção de elementos ordenados.
Busca de elementos já presentes (hit).
Busca de elementos não presentes (miss).
Cenários com diferentes padrões de acesso (Uniforme, Scan, Zipf).

5.7 Métricas coletadas
ns/op (tempo médio por operação) e tempo total.
Consistência: variação no tempo das operações sob diferentes padrões.
Escalabilidade: análise de como o desempenho se degrada com o aumento da carga. Ex.: Árvore Splay mantém O(log n) no pior caso, enquanto LinkedList + HashMap tende a manter O(1) em todas as operações.

5.8 Análise e Apresentação
Os resultados serão apresentados em gráficos comparativos, relacionando:
Tempo de execução × Carga.
Análise das causas dos resultados, com base na complexidade das estruturas.


6. Conclusão
Este plano de experimento fornecerá base sólida para avaliar a eficiência do LRU implementado com Árvore Splay e com LinkedList + HashMap, destacando vantagens, limitações e cenários ideais para cada abordagem em ambientes que demandam alta performance.


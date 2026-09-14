Tech Challenge Ager 2026

Tecnologias: Java 8, Struts 2, JSP, JDBC puro (sem ORM), H2 em memória, Bootstrap 5, Apache POI pra exportar o relatório em Excel, testes com JUnit 5 e Mockito, medição de coverage feito com JaCoCo.

Rodando o projeto:

mvn clean install
mvn jetty:run

OU 

run as -> maven build -> "jetty:run" no goals -> run.


Acessa em http://localhost:8080/avaliacao

Features:

- CRUD de funcionário, agenda e compromisso,
- Agenda tem período de disponibilidade (manhã, tarde ou ambos), e o compromisso não pode ser criado fora desse horário,
- Não dá pra excluir uma agenda que já tem compromisso vinculado,
- Excluir um funcionário exclui os compromissos dele junto,
- Relatório de compromissos por período, com exportação em Excel.

Features extras:

- Filtro de compromissos com múltiplos campos ao mesmo tempo (código, funcionário, agenda, período, data), diferente do combo único de busca em funcionário/agenda,
- Cobertura de testes medida com JaCoCo.

Testes:

mvn clean test

OU

run as -> maven build -> "clean test" no goals -> run


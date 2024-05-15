# API:
Software back-end desenvolvido em Java + Spring boot para simples gerenciamento de projetos de automação residencial.

Para executar o código em sua máquina siga os passos:

- Caso não tenha instalado o Postgresql em seu computador siga as instruções de download oficiais: https://www.postgresql.org/download/.
- Faça o download do ramo main do git desse projeto.
- Abra o projeto via Intellij ou outra IDE Java de preferência.
- Se necessário altere os atributos spring.datasource.url, spring.datasource.username e spring.datasource.password presentes no aquivos application.properties com os valores corretos de url, usuário e senha para acesso ao seu banco postgresql local.
- Inicie a aplicação.
- Agora é possível testar os endpoints da API via: http://localhost:8080/swagger-ui/index.html.
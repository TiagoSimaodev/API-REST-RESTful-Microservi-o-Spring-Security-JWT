🔐 API RESTful com Spring Boot, Microserviços e JWT

Uma aplicação backend robusta desenvolvida com Spring Boot, projetada para demonstrar boas práticas em arquitetura RESTful, microsserviços e segurança com JWT.

Este projeto serve como base para sistemas modernos que exigem autenticação segura, escalabilidade e integração com aplicações front-end.

✨ Funcionalidades Principais

Autenticação e Autorização Segura

Login com credenciais e geração de token JWT

Proteção de rotas privadas

Refresh token para renovação de sessões

CRUD de Usuários

Cadastro, atualização, exclusão e listagem de usuários

Validação de dados e respostas padronizadas

Arquitetura de Microsserviços

Serviços independentes comunicando-se via REST

Facilidade de escalabilidade horizontal

Tratamento Global de Exceções

Retorno padronizado de erros para o cliente

Logs estruturados para monitoramento

Boas Práticas REST

Padrões de nomenclatura

Uso de status HTTP corretos

Versionamento de API

📦 Tecnologias Utilizadas

Java 17

Spring Boot 3

Spring Web

Spring Data JPA

Spring Security + JWT

Hibernate (ORM)

PostgreSQL (persistência de dados)

Maven (gerenciamento de dependências)

JUnit 5 (testes unitários)

Lombok (produtividade)

🚀 Como Executar o Projeto
Pré-requisitos

Java 17 instalado

Maven 3+

PostgreSQL em execução

Passos
# Clone o repositório
git clone https://github.com/tiagosimaodev/API-REST-RESTful-Microservico-Spring-Security-JWT.git

# Acesse a pasta do projeto
cd API-REST-RESTful-Microservico-Spring-Security-JWT

# Configure o banco de dados no application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sua-base
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha

# Compile e execute
mvn spring-boot:run


A API estará disponível em:
👉 http://localhost:8080/api/v1

📄 Exemplos de Endpoints
🔑 Autenticação
POST /auth/login
{
  "email": "usuario@email.com",
  "senha": "123456"
}

👤 Usuários
GET /usuarios
Authorization: Bearer <seu-token>

📈 Próximas Funcionalidades (Roadmap)

Integração com Docker e Docker Compose

Monitoramento com Spring Actuator

Integração com mensageria (Kafka ou RabbitMQ)

Deploy em ambiente cloud (Heroku/AWS)

🤝 Contribuição

Contribuições são bem-vindas! Para colaborar:

Faça um fork do repositório

Crie uma branch para sua feature (git checkout -b feature/minha-feature)

Commit suas alterações (git commit -m 'Adiciona nova feature')

Push para a branch (git push origin feature/minha-feature)

Abra um Pull Request

📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE
 para mais detalhes.

👤 Autor

Francisco Tiago Rodrigues Simão

💼 LinkedIn

💻 GitHub

📚-API-REST-RESTful /Microserviço-Spring Security + JWT

Esta API é uma aplicação backend robusta desenvolvida com Spring Boot, que cobre desde fundamentos dos microserviços, arquitetura RESTful, 
até temas avançados como segurança com JWT, controle de erros, cache, versionamento, integração CORS e consumo de APIs externas.


🚀 Tecnologias Utilizadas

Java 17+

Spring Boot 

Spring bot REST / RESTFULL

Spring Security + JWT

Spring Data JPA

Maven

HikariCP (pool de conexões)

PostgreSQL 

PostMan (para testes)

🔧 Funcionalidades Implementadas

 microserviços e integração via APIs REST

Cadastro de Usuario

Modelagem correta de URIs e regras RESTful

Desenvolvimento da API RESTful

Criação de controllers, serviços e endpoints REST (GET, POST, PUT, DELETE)

Passagem de parâmetros e customização de URLs

Retorno de dados JSON

Relacionamento um-para-muitos e tratamento de JSON para evitar recursividade

Cadastro, atualização e remoção via endpoints

Testes e Ferramentas
Testes com PostMan para validação e debug de endpoints

Empacotamento e Deploy
Geração de artefatos JAR e WAR

Implantação da API em servidores e hospedagem

Segurança
Configuração de Spring Security com papéis (ROLE)

Autenticação e autorização usando JWT

Implementação completa do fluxo JWT (filtros, geração, validação)

Controle de acesso e restrição de dados sensíveis

Atualização e tratamento de tokens expirados

Cross-Origin Resource Sharing (CORS)
Configuração granular e testes de CORS para acesso externo via AJAX

Funcionalidades Avançadas
Versionamento da API

Pool de conexões com HikariCP para alta performance

Implementação e gerenciamento de cache (CacheEvict, CachePut)

Controle de erros customizado com @ControllerAdvice e @ExceptionHandler

Padrão DTO para transferência de dados entre camadas

Consumo da API externa ViaCEP para preenchimento de dados

🖥️ Como executar
Clone o repositório:

bash
Copiar
Editar
git clone https://github.com/seuusuario/seuprojeto.git
Importe o projeto em sua IDE (STS, IntelliJ, Eclipse).

Configure o banco de dados no arquivo application.properties ou application.yml.

Execute a aplicação pela classe principal @SpringBootApplication.

Utilize PostMan para consumir e testar os endpoints.

Para gerar artefatos executáveis:

bash
Copiar
Editar
mvn clean package
Faça o deploy do WAR em seu servidor, se necessário.

📂 Estrutura do Projeto (MVC)
css
Copiar
Editar
src/
└── main/
    ├── java/
    │   └── com.seuprojeto/
    │       ├── controller/
    │       ├── model/
    │       ├── repository/
    │       ├── service/
    │       └── security/
    └── resources/
        ├── static/
        ├── templates/
        └── application.properties
🧪 Validações
Backend: uso de anotações para validação de campos (Spring Validation).

Frontend (quando aplicável): uso de JavaScript para validação em tempo real e feedback.

🚧 Status do Projeto
Será implementado agora o front-end que será com Angular, em breve o projeto completo.

👨‍💻 Autor
Tiago Simão
Desenvolvedor Java Full Stack
📧 tiagosimaorodri123@gmail.com
GitHub: https://github.com/TiagoSimaodev
LinkedIn: https://www.linkedin.com/in/tiago-simao-685015193/

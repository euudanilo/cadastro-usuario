<h1 align="center">🚀 Cadastro de Usuário API</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" />
  <img src="https://img.shields.io/badge/Tests-JUnit%20%2B%20Mockito-25A162?style=for-the-badge" />
  <img src="https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge" />
</p>

---

<h2>📌 Sobre o Projeto</h2>

<p>
API RESTful desenvolvida com <strong>Java 21 + Spring Boot</strong>,
utilizando <strong>PostgreSQL</strong> como banco de dados e preparada para execução via <strong>Docker</strong>.
</p>

<p>
O projeto implementa um CRUD completo de usuários com separação clara de camadas,
tratamento global de exceções e testes unitários isolados.
</p>

---

<h2>🧠 Arquitetura</h2>

<pre>
Controller → Service → Repository → Database
</pre>

<ul>
  <li><strong>Controller</strong> → Entrada HTTP</li>
  <li><strong>Service</strong> → Regras de negócio</li>
  <li><strong>Repository</strong> → Acesso ao banco com JPA</li>
  <li><strong>Exceptions personalizadas</strong></li>
  <li><strong>GlobalExceptionHandler</strong></li>
</ul>

---

<h2>🛠️ Tecnologias Utilizadas</h2>

<ul>
  <li>Java 21</li>
  <li>Spring Boot</li>
  <li>Spring Data JPA</li>
  <li>PostgreSQL</li>
  <li>Docker</li>
  <li>Maven</li>
  <li>JUnit 5</li>
  <li>Mockito</li>
</ul>

---

<h2>📂 Estrutura do Projeto</h2>

<pre>
cadastro-usuario
│
├── controller
├── business
├── infrastructure
│   ├── entity
│   ├── repository
│   └── exceptions
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
</pre>

---

<h2>▶️ Como Executar</h2>

<h3>1️⃣ Clonar o repositório</h3>

<pre>
git clone https://github.com/euudanilo/cadastro-usuario.git
cd cadastro-usuario
</pre>

<h3>2️⃣ Subir com Docker</h3>

<pre>
docker-compose up --build
</pre>

<p>
A aplicação estará disponível em:
</p>

<pre>
http://localhost:8080/usuario
</pre>

---

<h2>🔌 Endpoints</h2>

<table>
  <tr>
    <th>Método</th>
    <th>Endpoint</th>
    <th>Descrição</th>
  </tr>
  <tr>
    <td>POST</td>
    <td>/usuario</td>
    <td>Criar usuário</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/usuario</td>
    <td>Listar usuários</td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/usuario/{email}</td>
    <td>Buscar por email</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/usuario/{email}</td>
    <td>Atualizar usuário</td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>/usuario/{email}</td>
    <td>Remover usuário</td>
  </tr>
</table>

---

<h2>📥 Exemplo de Requisição</h2>

<pre>
{
  "nome": "João Silva",
  "email": "joao@email.com"
}
</pre>

---

<h2>🧪 Testes</h2>

<pre>
mvn test
</pre>

<p>
Os testes utilizam Mockito para mockar dependências e validar respostas HTTP.
</p>

---

<h2>⚙️ Configuração do Banco</h2>

<pre>
spring.datasource.url=jdbc:postgresql://db:5432/cadastro_usuario
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
</pre>

<p>Nenhuma variável de ambiente é necessária para execução via Docker.</p>

---

<h2>🚀 Melhorias Futuras</h2>

<ul>
  <li>Validação com Bean Validation</li>
  <li>Documentação com Swagger</li>
  <li>Autenticação com JWT</li>
  <li>CI/CD com GitHub Actions</li>
  <li>Deploy em nuvem</li>
</ul>

---

<h2>📄 Licença</h2>

<p>Projeto de uso educacional.</p>

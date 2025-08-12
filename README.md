# Api_Doacoes
🚀 Como Executar:

Preparar ambiente:
bash# Subir PostgreSQL
docker-compose up -d

# Verificar se está rodando
docker ps

Executar aplicação:
bash# Compilar
mvn clean compile

# Executar
mvn spring-boot:run

Testar API:

Use os exemplos em test-requests.http
Ou acesse: http://localhost:8080/api/doacoes



🔧 Funcionalidades Implementadas:
✅ CRUD Completo:

✨ Incluir doação (POST /api/doacoes)
🔍 Consultar todas (GET /api/doacoes)
🔍 Consultar por ID (GET /api/doacoes/{id})
🔍 Consultar por CPF (GET /api/doacoes/cpf/{cpf})
🔍 Consultar por nome (GET /api/doacoes/nome/{nome})
🧮 Total por CPF (GET /api/doacoes/total/cpf/{cpf})
🗑️ Deletar (DELETE /api/doacoes/{id})

✅ Validações:

Valor > 0,00
CPF obrigatório (11 dígitos)
Nome obrigatório
Email formato válido
Tratamento de erros completo

✅ Tecnologias:

Spring Boot 3.2
PostgreSQL + Docker
Lombok @Data
Validation API
Exception Handling
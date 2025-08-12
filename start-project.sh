#!/bin/bash

echo "🚀 Iniciando projeto Doações API"
echo "================================"

# Verificar se Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não encontrado. Por favor, instale o Docker primeiro."
    exit 1
fi

# Verificar se Docker Compose está instalado
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose não encontrado. Por favor, instale o Docker Compose primeiro."
    exit 1
fi

# Parar containers existentes (se houver)
echo "🛑 Parando containers existentes..."
docker-compose down

# Iniciar PostgreSQL
echo "🐘 Iniciando PostgreSQL..."
docker-compose up -d

# Aguardar PostgreSQL ficar pronto
echo "⏳ Aguardando PostgreSQL ficar pronto..."
sleep 10

# Verificar se PostgreSQL está rodando
if docker ps | grep -q "postgres-doacoes"; then
    echo "✅ PostgreSQL está rodando!"
else
    echo "❌ Erro ao iniciar PostgreSQL"
    exit 1
fi

# Compilar e executar aplicação Spring Boot
echo "☕ Compilando e iniciando aplicação Spring Boot..."
echo "   (Certifique-se de ter Java 17 e Maven instalados)"

# Verificar se Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale o Maven primeiro."
    echo "   Ou use: ./mvnw spring-boot:run"
    exit 1
fi

# Limpar e compilar
mvn clean compile

# Executar aplicação
echo "🏃‍♂️ Executando aplicação..."
mvn spring-boot:run &

echo ""
echo "🎉 Projeto iniciado com sucesso!"
echo "📊 PostgreSQL: http://localhost:5432"
echo "🌐 API: http://localhost:8080"
echo "📝 Documentação: http://localhost:8080/api/doacoes"
echo ""
echo "Para testar a API, use os exemplos em test-requests.http"
echo "Para parar tudo: docker-compose down && pkill -f spring-boot"
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

# Copia o POM primeiro para aproveitar cache das dependências
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Copia o código-fonte e empacota
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy
WORKDIR /opt/app

# Cloud Run usa a variável $PORT; default 8080 localmente
ENV PORT=8080
EXPOSE 8080

# Copia o jar gerado
# (se houver só 1 jar em target, o wildcard funciona)
COPY --from=builder /workspace/target/*.jar /opt/app/app.jar

# Faz o app escutar na porta do Cloud Run ($PORT)
ENTRYPOINT ["sh","-c","java -jar /opt/app/app.jar"]
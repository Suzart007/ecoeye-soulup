# ---------- Etapa 1: compila a API com Maven ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q -DskipTests package

# ---------- Etapa 2: imagem final, so com o Java e o .jar ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV TZ=America/Sao_Paulo
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -Duser.timezone=America/Sao_Paulo"
# A Vercel envia o trafego para a porta 80 do container (lida no application.properties).
# Se a variavel PORT for cadastrada no projeto da Vercel, ela substitui este valor.
ENV PORT=80
COPY --from=build /app/target/soulup-api.jar app.jar
EXPOSE 80
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

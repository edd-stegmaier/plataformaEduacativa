FROM maven:3.9.9-eclipse-temurin-21 AS buildstage

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

COPY src/ src/
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre

WORKDIR /app

ENV DB_WALLET_PATH=/opt/oracle/wallet

RUN mkdir -p /opt/oracle/wallet

COPY --from=buildstage /app/target/plataformaEducativa-1.0.0.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
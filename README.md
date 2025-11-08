# Spring vs. Quarkus

## Spring
Start framework:
```
docker compose --profile spring up
```

[DIY Starter](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.5.7&packaging=jar&configurationFileFormat=properties&jvmVersion=25&groupId=dev.rabauer.ai.demo&artifactId=spring-backend&name=spring-backend&description=Ai%20Demo&packageName=dev.rabauer.ai.demo.spring-backend&dependencies=web,spring-ai-ollama,data-jpa,postgresql)

## Quarkus
Start framework:
```
docker compose --profile quarkus up
```

[DIY Starter](https://code.quarkus.io/?g=dev.rabauer.ai.demo&a=quarkus-backend&nc=true&e=io.quarkiverse.langchain4j%3Aquarkus-langchain4j-ollama&e=smallrye-openapi&e=rest&e=hibernate-orm-panache&e=jdbc-postgresql)

## Frontend

After starting up the frontend is available at [http://localhost:8081](http://localhost:8081).

## Architecture 

```mermaid
flowchart TD
    subgraph DockerCompose["Docker Compose (profiles)"]
        subgraph Frontend["Frontend"]
            Vaadin["Vaadin App"]
        end

        subgraph Backend["Backend (choose profile)"]
            Spring["Spring + Spring AI"]
            Quarkus["Quarkus + LangChain4J"]
        end

        subgraph Ollama["Ollama Container"]
            Llama["Llama3.2"]
        end

        subgraph Database["Database"]
            Postgres[(PostgreSQL)]
        end
    end

    %% Connections
    Vaadin -->|REST Endpoints| Spring
    Vaadin -->|REST Endpoints| Quarkus
    Vaadin -->|Read Data| Postgres

    Spring -->|Requests| Llama
    Spring -->|Write Data| Postgres
    Quarkus -->|Requests| Llama
    Quarkus -->|Write Data| Postgres
```
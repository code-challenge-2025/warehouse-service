# warehouse-service

## Ways to test locally

MAC:
    echo "sensor_id=t1; value=30" | socat - UDP:127.0.0.1:3344

## Kafka Docs

https://docs.spring.io/spring-kafka/reference/

## Docker/kafka

open folder "docker" and run:
`docker compose up -d `

## Configurations (CUSTOMS)

| GROUP     | DESCRIPTION                  | DEFAULT VALUE       |
|-----------|------------------------------|---------------------|
| kafka     | kafka topic name             | code-challenge-2025 |
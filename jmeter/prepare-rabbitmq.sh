#!/usr/bin/env sh
set -eu

BASE_URL=${RABBITMQ_MANAGEMENT_URL:-http://127.0.0.1:15672}
USER=${RABBITMQ_USER:-myuser}
PASS=${RABBITMQ_PASSWORD:-secret}

printf 'Declaring exchange...\n' >&2
curl --fail-with-body --silent --show-error -u "$USER:$PASS" -H 'content-type: application/json' \
  -X PUT "$BASE_URL/api/exchanges/%2F/spring-boot-exchange" \
  -d '{"type":"topic","durable":true,"auto_delete":false,"internal":false,"arguments":{}}'
printf 'Declaring queue...\n' >&2
curl --fail-with-body --silent --show-error -u "$USER:$PASS" -H 'content-type: application/json' \
  -X PUT "$BASE_URL/api/queues/%2F/spring-boot" \
  -d '{"durable":false,"auto_delete":false,"arguments":{}}'
printf 'Creating queue binding...\n' >&2
curl --fail-with-body --silent --show-error -u "$USER:$PASS" -H 'content-type: application/json' \
  -X POST "$BASE_URL/api/bindings/%2F/e/spring-boot-exchange/q/spring-boot" \
  -d '{"routing_key":"foo.bar.#","arguments":{}}'
printf 'RabbitMQ exchange, queue, and binding are ready.\n'

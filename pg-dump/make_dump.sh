#!/bin/bash

CONTAINER_NAME="postgres"
DB_NAME="postgres"
DB_USER="postgres"
BACKUP_DIR="pg-dump"

mkdir -p "$BACKUP_DIR"

DUMP_FILE="$BACKUP_DIR/dump.sql"

if ! docker ps --format '{{.Names}}' | grep -q "^${CONTAINER_NAME}$"; then
  echo "Kontener ${CONTAINER_NAME} nie działa!"
  exit 1
fi

echo "Tworzę backup bazy '${DB_NAME}' z kontenera '${CONTAINER_NAME}'..."
docker exec -t "$CONTAINER_NAME" pg_dump -U "$DB_USER" "$DB_NAME" > "$DUMP_FILE"

if [ $? -eq 0 ]; then
  echo "Backup zapisany w: $DUMP_FILE"
else
  echo "Błąd przy tworzeniu dumpa!"
  exit 1
fi

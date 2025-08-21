FROM gradle:8.13.0-jdk17
WORKDIR /app
COPY . .

# Without tests - much faster
# CMD ["./gradlew", "bootRun"]

# With tests
CMD ["sh", "-c", "./gradlew test && ./gradlew bootRun"]
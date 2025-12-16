#!/usr/bin/env sh

# GitHub Actions ko seedha gradlew use karne do
GRADLE_OPTS="-Dorg.gradle.daemon=false"
GRADLE_WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"

if [ ! -f $GRADLE_WRAPPER_JAR ]; then
    echo "Error: The Gradle wrapper JAR is missing. Did you forget to commit it?"
    exit 1
fi

java ${GRADLE_OPTS} -jar "$GRADLE_WRAPPER_JAR" "$@"


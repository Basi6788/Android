#!/usr/bin/env sh

# Ye basic gradlew script hai. Android Studio mein ye bohot bada hota hai.
# GitHub Actions ke liye itna kaafi hona chahiye agar hum './gradlew' command use kar rahe hain.

GRADLE_OPTS="-Dorg.gradle.daemon=false"

GRADLE_WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"

if [ ! -f $GRADLE_WRAPPER_JAR ]; then
    echo "Error: The Gradle wrapper JAR is missing. Did you forget to commit it?"
    exit 1
fi

java ${GRADLE_OPTS} -jar "$GRADLE_WRAPPER_JAR" "$@"


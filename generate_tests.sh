#!/bin/bash
PATHBINPROJECT="/Users/gabriellacerda/GitHubGabrielLacerda/GCViewerV1/target/classes"
CLASS_PATH="/Users/gabriellacerda/GitHubGabrielLacerda/GCViewerV1/target/classes:/Users/gabriellacerda/PurityTrackerTool/src/main/java/org/example/libs/randoop-all-4.3.2.jar"
CLASSLIST_FILE="/Users/gabriellacerda/PurityTrackerTool/src/main/java/org/example/txtFiles/comuns_classes.txt"
OMIT_METHODS_FILE="/Users/gabriellacerda/PurityTrackerTool/src/main/java/org/example/txtFiles/omited_methods.txt"
OUTPUT_LIMIT=100
JUNIT_OUTPUT_DIR="/Users/gabriellacerda/GitHubGabrielLacerda/GCViewer/src"

java -classpath "$CLASS_PATH" randoop.main.Main gentests \
    --classlist="$CLASSLIST_FILE" \
    --omit-methods-file="$OMIT_METHODS_FILE" \
    --output-limit="$OUTPUT_LIMIT" \
    --no-error-revealing-tests=true \
    --flaky-test-behavior=DISCARD \
    --junit-output-dir="$JUNIT_OUTPUT_DIR"
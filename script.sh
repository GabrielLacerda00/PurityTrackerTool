#!/bin/bash

CLASS_PATH="/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src/out:/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar"
CLASSLIST_FILE="/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/omited_methods.txt"
OMIT_METHODS_FILE="/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/comuns_classes.txt"
OUTPUT_LIMIT=100
JUNIT_OUTPUT_DIR="/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src"

java -classpath "$CLASS_PATH" randoop.main.Main gentests \
    --classlist="$CLASSLIST_FILE" \
    --omit-methods-file="$OMIT_METHODS_FILE" \
    --output-limit="$OUTPUT_LIMIT" \
    --no-error-revealing-tests=true \
    --flaky-test-behavior=DISCARD \
    --junit-output-dir="$JUNIT_OUTPUT_DIR"


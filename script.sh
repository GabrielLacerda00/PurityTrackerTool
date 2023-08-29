#!/bin/bash

CLASS_PATH="/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoTest/target/classes/org/example:/Users/gabriellacerda/GitHubGabrielLacerda/PIBICTOOL/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar"
CLASSLIST_FILE="ProdutoDTO"
OMIT_METHODS_FILE="/Users/gabriellacerda/GitHubGabrielLacerda/PIBICTOOL/PurityTrackerTool/src/main/java/org/example/txtFiles/omited_methods.txt"
OUTPUT_LIMIT=100
JUNIT_OUTPUT_DIR="/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src"

java -classpath "$CLASS_PATH" randoop.main.Main gentests \
    --testclass="$CLASSLIST_FILE" \
    --omit-methods-file="$OMIT_METHODS_FILE" \
    --output-limit="$OUTPUT_LIMIT" \
    --no-error-revealing-tests=true \
    --flaky-test-behavior=DISCARD \
    --junit-output-dir="$JUNIT_OUTPUT_DIR"


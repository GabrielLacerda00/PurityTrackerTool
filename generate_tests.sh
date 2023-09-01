#!/bin/bash
PATHBINPROJECT="/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestePIBICTool/OneRenameForTwoCaller/CodeOrigin/bin"
CLASS_PATH="/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestePIBICTool/OneRenameForTwoCaller/CodeOrigin/bin:/Users/gabriellacerda/GitHubGabrielLacerda/PIBICTOOL/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar"
CLASSLIST_FILE="/Users/gabriellacerda/GitHubGabrielLacerda/PIBICTOOL/PurityTrackerTool/src/main/java/org/example/txtFiles/comuns_classes.txt"
OMIT_METHODS_FILE="/Users/gabriellacerda/GitHubGabrielLacerda/PIBICTOOL/PurityTrackerTool/src/main/java/org/example/txtFiles/omited_methods.txt"
OUTPUT_LIMIT=100
JUNIT_OUTPUT_DIR="/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestePIBICTool/OneRenameForTwoCaller/CodeDestiny/src"

java -classpath "$CLASS_PATH" randoop.main.Main gentests \
    --classlist="$CLASSLIST_FILE" \
    --omit-methods-file="$OMIT_METHODS_FILE" \
    --output-limit="$OUTPUT_LIMIT" \
    --no-error-revealing-tests=true \
    --flaky-test-behavior=DISCARD \
    --junit-output-dir="$JUNIT_OUTPUT_DIR"
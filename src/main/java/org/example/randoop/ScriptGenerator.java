package org.example.randoop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScriptGenerator {

    public static void main(String[] args) {

        String PATHBINPROJECT = "/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoVersionOrigin/src/out";
        String CLASS_PATH = "/Users/gabriellacerda/GitHubGabrielLacerda/PIBICTOOL/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar";
        String CLASSLIST_FILE = "/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/omited_methods.txt";
        String OMIT_METHODS_FILE = "/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/comuns_classes.txt";
        int OUTPUT_LIMIT = 100;
        String JUNIT_OUTPUT_DIR = "/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src";

        generateScript(CLASS_PATH, CLASSLIST_FILE, OMIT_METHODS_FILE, OUTPUT_LIMIT, JUNIT_OUTPUT_DIR, PATHBINPROJECT);
    }

    public static void generateScript(String classPaath, String classlistFile, String omitMethodsFile,
                                      int outputLimit, String junitOutputDir,String PATHBINPROJECT) {

        String classPath = PATHBINPROJECT +":"+classPaath;

        String scriptContent = "#!/bin/bash" + "\n" +
                "PATHBINPROJECT=\"" + PATHBINPROJECT + "\"\n" +
                "CLASS_PATH=\"" + classPath + "\"\n" +
                "CLASSLIST_FILE=\"" + classlistFile + "\"\n" +
                "OMIT_METHODS_FILE=\"" + omitMethodsFile + "\"\n" +
                "OUTPUT_LIMIT=" + outputLimit + "\n" +
                "JUNIT_OUTPUT_DIR=\"" + junitOutputDir + "\"\n\n" +
                "java -classpath \"$CLASS_PATH\" randoop.main.Main gentests \\\n" +
                "    --classlist=\"$CLASSLIST_FILE\" \\\n" +
                "    --omit-methods-file=\"$OMIT_METHODS_FILE\" \\\n" +
                "    --output-limit=\"$OUTPUT_LIMIT\" \\\n" +
                "    --no-error-revealing-tests=true \\\n" +
                "    --flaky-test-behavior=DISCARD \\\n" +
                "    --junit-output-dir=\"$JUNIT_OUTPUT_DIR\"";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generate_tests.sh"))) {
            writer.write(scriptContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

cls
ECHO on
java -classpath /Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src/out;/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar randoop.main.Main gentests --classlist=/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/omited_methods.txt --omit-methods-file=/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/comuns_classes.txt --output-limit=100 --no-error-revealing-tests=true --flaky-test-behavior=DISCARD --junit-output-dir=/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src
pause
break > %0

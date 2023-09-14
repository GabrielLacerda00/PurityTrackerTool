#!/bin/bash

CLASS_PATH="/Users/gabriellacerda/Downloads/junit-4.12.jar:/Users/gabriellacerda/PurityTrackerTool/tmp/GCViewerV1/src/test/java/ " + "org.junit.runner.JUnitCore"
CLASS_NAME="RegressionTest0.java"

java -cp $CLASS_PATH $CLASS_NAME

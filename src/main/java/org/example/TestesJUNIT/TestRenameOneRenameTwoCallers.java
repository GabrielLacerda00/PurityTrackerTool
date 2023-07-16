package org.example.TestesJUNIT;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.example.refactoringMiner.refactoringMinerHandlerBetweenCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.callerWaited;
import org.example.util.objectOutputRefactMiner;
import org.example.controllerTool;
import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;
import org.junit.jupiter.api.BeforeAll;

public class TestRenameOneRenameTwoCallers {
    @Test
    public void testOneRenameTwoCaller() throws Exception {
        //Caminho do projeto origem
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCaller/CodeOrigin";
        //Caminho do projeto destino
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCaller/CodeDestiny";
        refactoringMinerHandlerBetweenCommits.handlerPathsDirs(pathDir01,pathDir02);

        refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git",
                "a5d9055020fe8b5d11cadd1bc99f28358ad2776f",
                "d3787c49b276e7489248f41e5800d9b7b38dcc6e");

        ArrayList<objectOutputRefactMiner> objectsRRMiner = new ArrayList<>();
        objectsRRMiner.addAll(refactoringMinerHandlerBetweenCommits.getObjectsRMiners());

        gumTreeDiffOutputRenameMethodHandler.renameMethodHandler(pathDir01,pathDir02);
        ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();
        listRenamesObjects.addAll(gumTreeDiffOutputRenameMethodHandler.getListaConvertida());

        List<String> types = new ArrayList<String>();

        types.addAll(controllerTool.checkRenameMethod(objectsRRMiner,listRenamesObjects));

        List<String> expectedType = new ArrayList<String>();
        expectedType.add("Pure");
        expectedType.add("Pure");

        assertArrayEquals(expectedType.toArray(), types.toArray());
        expectedType.clear();
    }
}
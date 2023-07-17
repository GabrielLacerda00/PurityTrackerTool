package org.example.TestesJUNIT;

import org.example.controllerTool;
import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;
import org.example.refactoringMiner.refactoringMinerHandlerBetweenCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.objectOutputRefactMiner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class TestRenameOneRenameForMissingCallers {
    @Test
    public void testOneRenameForMissingCaller() throws Exception {
        //Caminho do projeto origem
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeOrigin";
        //Caminho do projeto destino
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeDestiny";
        refactoringMinerHandlerBetweenCommits.handlerPathsDirs(pathDir01,pathDir02);

        refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git",
                "9efa63633fbff505b84d8fba97d7a4292e7e5561",
                "91e95a85850a61e81623386eeca2f495f9aec062");

        ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();
        objectsRMiner = refactoringMinerHandlerBetweenCommits.getObjectsRMiners();

        gumTreeDiffOutputRenameMethodHandler.renameMethodHandler(pathDir01,pathDir02);
        ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();
        //listRenamesObjects = gumTreeDiffOutputRenameMethodHandler.getListaConvertida();

        List<String> types = new ArrayList<String>();

        types = controllerTool.checkRenameMethod(objectsRMiner,listRenamesObjects);

        List<String> expectedType = new ArrayList<String>();
        expectedType.add("Pure");
        expectedType.add("Floss");

        assertArrayEquals(expectedType.toArray(), types.toArray());
        expectedType.clear();
    }
}

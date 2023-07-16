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
//import org.junit.jupiter.api.Test;

public class TestPurityToolRenames {


	@Test
	public void testOneRenameOneCaller() throws Exception {
		 //Caminho do projeto origem
		 String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeOrigin";
		 //Caminho do projeto destino
		 String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeDestiny";
		 refactoringMinerHandlerBetweenCommits.handlerPathsDirs(pathDir01,pathDir02);
		 
		 refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git",  
			        "a72b2434780b583bfeeb309d6d8330648b898cfe",                                                                                
			        "05ffc9d88525f2a3ec34eabe5f80a7e621e7a95a");
		 
		 ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();
		 objectsRMiner.addAll(refactoringMinerHandlerBetweenCommits.getObjectsRMiners());
		 
		 gumTreeDiffOutputRenameMethodHandler.renameMethodHandler(pathDir01,pathDir02);
		 ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();
		 listRenamesObjects.addAll(gumTreeDiffOutputRenameMethodHandler.getListaConvertida());

		 List<String> types = new ArrayList<String>();
		 
		 types.addAll(controllerTool.checkRenameMethod(objectsRMiner,listRenamesObjects));
		 
		 List<String> expectedType = new ArrayList<String>();
		 expectedType.add("Pure");
		 expectedType.add("Pure");
		 
		 assertArrayEquals(expectedType.toArray(), types.toArray());
		 expectedType.clear();
	}

}

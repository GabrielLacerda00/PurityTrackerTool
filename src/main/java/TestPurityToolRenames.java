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

public class TestPurityToolRenames {
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testOneRenameOneCaller() throws Exception {
		 //Caminho do projeto origem
		 String pathDir01 = "//Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeOrigin";
		 //Caminho do projeto destino
		 String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeDestiny";
		 refactoringMinerHandlerBetweenCommits.handlerPathsDirs(pathDir01,pathDir02);
		 
		 refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git",  
			        "a72b2434780b583bfeeb309d6d8330648b898cfe",                                                                                
			        "05ffc9d88525f2a3ec34eabe5f80a7e621e7a95a");
		 
		 ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();
		 objectsRMiner = refactoringMinerHandlerBetweenCommits.getObjectsRMiners();
		 
		 gumTreeDiffOutputRenameMethodHandler.main(null);
		 ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();
		 listRenamesObjects = gumTreeDiffOutputRenameMethodHandler.getListaConvertida();
		 
		 List<String> types = new ArrayList<String>();
		 
		 types = controllerTool.checkRenameMethod(objectsRMiner,listRenamesObjects);
		 
		 List<String> expectedType = new ArrayList<String>();
		 expectedType.add("Pure");
		 expectedType.add("Pure");
		 
		 assertArrayEquals(expectedType.toArray(), types.toArray());		 
	}

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

		ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();
		objectsRMiner = refactoringMinerHandlerBetweenCommits.getObjectsRMiners();

		gumTreeDiffOutputRenameMethodHandler.main(null);
		ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();
		listRenamesObjects = gumTreeDiffOutputRenameMethodHandler.getListaConvertida();

		List<String> types = new ArrayList<String>();

		types = controllerTool.checkRenameMethod(objectsRMiner,listRenamesObjects);

		List<String> expectedType = new ArrayList<String>();
		expectedType.add("Pure");
		expectedType.add("Pure");

		assertArrayEquals(expectedType.toArray(), types.toArray());
	}

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

		gumTreeDiffOutputRenameMethodHandler.main(null);
		ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();
		listRenamesObjects = gumTreeDiffOutputRenameMethodHandler.getListaConvertida();

		List<String> types = new ArrayList<String>();

		types = controllerTool.checkRenameMethod(objectsRMiner,listRenamesObjects);

		List<String> expectedType = new ArrayList<String>();
		expectedType.add("Pure");
		expectedType.add("Floss");

		assertArrayEquals(expectedType.toArray(), types.toArray());
	}

}

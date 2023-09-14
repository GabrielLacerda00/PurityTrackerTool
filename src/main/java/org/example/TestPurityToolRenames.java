package org.example;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.example.mainFluxograma02;
import org.example.refactoringMiner.RMinerHandlerCommits;
import org.junit.Test;

import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;
import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;


public class TestPurityToolRenames {

	@Test
	@Order(1)
	public void testOneRenameOneCaller() throws Exception {
		String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeOrigin";
		String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeDestiny";
		String urlGit = "https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git";
		String commit01 = "a28390425fb88b9470cbd32ff1ecac2f38905bff";
		String commit02 = "b0aef74c3c1b344740e04dfbcb97aa1de5178815";

		RMinerHandlerCommits RMinerHandlerCommits = new RMinerHandlerCommits(pathDir01,pathDir02,urlGit,commit01,commit02);

		gumTreeDiffOutputRenameMethodHandler gumtTree = new gumTreeDiffOutputRenameMethodHandler(pathDir01,pathDir02);

		List<String> types = new ArrayList<String>();

		mainFluxograma02 main01 = new mainFluxograma02();

		types.addAll(main01.checkRenameMethod(RMinerHandlerCommits.getrMinerObjectsArrayList(), gumtTree.getListaConvertida()));

		List<String> expectedType = new ArrayList<String>();
		expectedType.add("Pure");
		expectedType.add("Pure");

		assertArrayEquals(expectedType.toArray(), types.toArray());

		expectedType.clear();

	}

	@Test
	@Order(2)
	public void testOneRenameTwoCaller() throws Exception {
		String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCaller/CodeOrigin";
		String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCaller/CodeDestiny";
		String urlGit = "https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git";
		String commit01 = "a5d9055020fe8b5d11cadd1bc99f28358ad2776f";
		String commit02 = "d3787c49b276e7489248f41e5800d9b7b38dcc6e";

		RMinerHandlerCommits rMinerHandlerCommitsTwo = new RMinerHandlerCommits(pathDir01,pathDir02,urlGit,commit01,commit02);

		gumTreeDiffOutputRenameMethodHandler gumtTreeTwo = new gumTreeDiffOutputRenameMethodHandler(pathDir01,pathDir02);

		List<String> typesTwo = new ArrayList<String>();

		mainFluxograma02 main02 = new mainFluxograma02();

		typesTwo.clear();
		typesTwo.addAll(main02.checkRenameMethod(rMinerHandlerCommitsTwo.getrMinerObjectsArrayList(), gumtTreeTwo.getListaConvertida()));

		List<String> expectedType02 = new ArrayList<String>();
		expectedType02.add("Pure");
		expectedType02.add("Pure");

		assertArrayEquals(expectedType02.toArray(), typesTwo.toArray());

	}

	@Test
	@Order(3)
	public void testOneRenameForMissingCaller() throws Exception {

		String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeOrigin";
		String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeDestiny";
		String urlGit = "https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git";
		String commit01 = "9efa63633fbff505b84d8fba97d7a4292e7e5561";
		String commit02 = "91e95a85850a61e81623386eeca2f495f9aec062";

		RMinerHandlerCommits rMinerHandlerCommits03 = new RMinerHandlerCommits(pathDir01, pathDir02, urlGit, commit01, commit02);

		gumTreeDiffOutputRenameMethodHandler gumtTree03 = new gumTreeDiffOutputRenameMethodHandler(pathDir01, pathDir02);

		List<String> types03 = new ArrayList<String>();

		mainFluxograma02 main03 = new mainFluxograma02();

		types03.addAll(main03.checkRenameMethod(rMinerHandlerCommits03.getrMinerObjectsArrayList(), gumtTree03.getListaConvertida()));

		List<String> expectedType03 = new ArrayList<String>();
		expectedType03.add("Pure");
		expectedType03.add("Floss");

		assertArrayEquals(expectedType03.toArray(), types03.toArray());

	}
}



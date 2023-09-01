import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import repository.FirstUnitTest;


public class JUnitTestRunner {

    public static void main(String[] args) throws ClassNotFoundException {
        JUnitTestRunner runner = new JUnitTestRunner();
        boolean allTestsPassed = runner.runTests();
        System.out.println(allTestsPassed ? "All tests passed." : "Some tests failed.");
    }

    public boolean runTests() throws ClassNotFoundException {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(Class.forName("/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoTestV2/src/RegressionTest.java"));

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        return result.wasSuccessful();
    }
}
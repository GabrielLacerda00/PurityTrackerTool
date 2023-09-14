import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MavenJUnitRunner {

    public static void compileWithMaven(String projectPath) {
        try {
            Process process = Runtime.getRuntime().exec("mvn test-compile", null, new File(projectPath));
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runTestsWithMaven(String projectPath, String testClassName) {
        try {
            String cmd = "mvn test -Dtest=" + testClassName;
            Process process = Runtime.getRuntime().exec(cmd, null, new File(projectPath));

            // Capturar e exibir saída padrão
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = stdInput.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Capturar e exibir saída de erro
            try (BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = stdError.readLine()) != null) {
                    System.err.println(line);
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String projectPath = "/Users/gabriellacerda/GitHubGabrielLacerda/GCViewer"; // Atualize com o caminho do seu projeto
        String testClassName = "com.tagtraum.perf.gcviewer.RegressionTest0"; // Nome completo da classe de teste

        compileWithMaven(projectPath);
        runTestsWithMaven(projectPath, testClassName);
    }
}
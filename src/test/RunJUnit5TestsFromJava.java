//org.package;

//import org.junit.platform.engine.discovery.ClassNameFilter;
//import org.junit.platform.engine.discovery.DiscoverySelectors;
//import org.junit.platform.launcher.Launcher;
//import org.junit.platform.launcher.LauncherDiscoveryRequest;
//import org.junit.platform.launcher.TestPlan;
//import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
//import org.junit.platform.launcher.core.LauncherFactory;
//import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
//import org.junit.platform.launcher.listeners.TestExecutionSummary;
//
//import java.io.PrintWriter;
//
//import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
//
//public class RunJUnit5TestsFromJava {
//    SummaryGeneratingListener listener = new SummaryGeneratingListener();
//
//    public void runOne() {
//        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
//                .selectors(DiscoverySelectors.selectClass("RegressionTest.class"))
//                .filters(ClassNameFilter.includeClassNamePatterns(".*Test"))
//                .build();
//        Launcher launcher = LauncherFactory.create();
//        TestPlan testPlan = launcher.discover(request);
//        launcher.registerTestExecutionListeners(listener);
//        launcher.execute(request);
//    }
//
//    public void runAll() {
//        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
//                .selectors(DiscoverySelectors.selectPackage("myTests"))
//                .filters(ClassNameFilter.includeClassNamePatterns(".*Test"))
//                .build();
//        Launcher launcher = LauncherFactory.create();
//        TestPlan testPlan = launcher.discover(request);
//        launcher.registerTestExecutionListeners(listener);
//        launcher.execute(request);
//    }
//
//    public static void main(String[] args) {
//        RunJUnit5TestsFromJava runner = new RunJUnit5TestsFromJava();
//        runner.runAll();
//
//        TestExecutionSummary summary = runner.listener.getSummary();
//        summary.printTo(new PrintWriter(System.out));
//    }
//}
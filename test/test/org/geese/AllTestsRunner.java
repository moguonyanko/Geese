package test.org.geese;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.org.geese.util.TestUtilities;
import test.org.geese.ci.classifier.TestClassifiers;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestUtilities.class,
	TestClassifiers.class,})
public class AllTestsRunner {
}
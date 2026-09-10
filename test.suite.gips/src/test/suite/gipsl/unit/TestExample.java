package test.suite.gipsl.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestExample extends AbstractParserTest {

	@Test
	public void testMinimalGipsl() throws Exception {
		var input = """
					package "gipsl.solverconfig.withoutsolverargs"
					import "http://www.eclipse.org/emf/2002/Ecore"

					config {
						solver := GUROBI;
					}
				""";

		var model = parseHelper.parse(input);

		Assertions.assertNotNull(model);
		validationTestHelper.assertNoIssues(model);

//		var errors = model.eResource().getErrors();
//		Assertions.assertTrue(errors.isEmpty(),
//				String.format("Unexpected errors: %s", IterableExtensions.join(errors, ", ")));
	}

}

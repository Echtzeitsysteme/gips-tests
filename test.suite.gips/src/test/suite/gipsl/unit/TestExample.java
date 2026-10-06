package test.suite.gipsl.unit;

import java.nio.file.Path;

import org.emoflon.gips.gipsl.tests.AbstractParserTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import test.suite.gips.utils.GipsTestUtils;

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

	@Test
	public void constantsAreAllowed() throws Exception {
		var input = """
				package "gipsl.local.test"
				//import "platform:/resource/gipsl.all.build.model/model/Model.ecore"
				import "%s"


				config {
					solver := GUROBI;
				}

				// condition vnodeNotMappedCondition = forbid vnodeIsMapped
				pattern vnodeIsMapped {
					host: SubstrateNode

					vnode: VirtualNode {
						-host -> host
					}
				}


				pattern vnodeNotMapped {
					vnode: VirtualNode
				}
				// when vnodeNotMappedCondition

				rule mapVnode {
					root: Root {
						-containers -> substrateContainer
						-containers -> virtualContainer
					}

					substrateContainer: SubstrateContainer {
						-substrateNodes -> snode
					}

					virtualContainer: VirtualContainer {
						-virtualNodes -> vnode
					}

					snode: SubstrateResourceNode

					vnode: VirtualResourceNode {
						++ -host -> snode
					}
				}

				mapping a to mapVnode;

				function objA with a {
					5
				}

				objective : max {
					3
				}
				""".formatted( //
				// build import URI to avoid 'platform' scheme which is not available outside of
				// eclipse
				GipsTestUtils.getLocalURIInTestDirectory(Path.of("gipsl.all.build.model", "model", "Model.ecore")) //
		);

		var model = parseHelper.parse(input);
		Assertions.assertNotNull(model);
		validationTestHelper.assertNoIssues(model);

		// TODO: some problems:

		// - "GipslValidator" expects to run within eclipse, thus it won't be able to
		// access eclipse specific classes and methods.
		// -> Can be solved by overriding the default GipsValidator with a 'Test'
		// Validator in GipslInjectorProvider that removes the problematic methods.

		// - The import statement expects an absolute URI, because the Test does not run
		// within eclipse "platform:..." does not work. The alternative "file:" scheme
		// works, but the URI _must_ be absolute.
		// -> Build URI on test runtime and inject (format) into text input.

		// - For some reasons, the 'condition/forbid' statement
		// 'condition vnodeNotMapped = forbid vnodeIsMapped'
		// can not be resolved by the GT scope provider.
		// -> no workaround found, avoid the 'forbid' statement for now

	}

}

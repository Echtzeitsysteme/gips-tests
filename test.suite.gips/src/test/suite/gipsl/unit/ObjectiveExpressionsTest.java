package test.suite.gipsl.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectiveExpressionsTest extends AbstractParserTest {

	@Test
	public void constantsAreAllowed() throws Exception {
		var input = """
				package "gipsl.local.test"
				import "platform:/resource/gipsl.all.build.model/model/Model.ecore"

				config {
					solver := GUROBI;
				}

				condition vnodeNotMapped = forbid vnodeIsMapped
				pattern vnodeIsMapped {
					host: SubstrateNode

					vnode: VirtualNode {
						-host -> host
					}
				}

				pattern vnodeNotMapped {
					vnode: VirtualNode
				}
				when vnodeNotMapped

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
				""";

		var model = parseHelper.parse(input);
		Assertions.assertNotNull(model);
		validationTestHelper.assertNoIssues(model);

		// TODO: won't work, "GipslValidator" expects to run within eclipse and won't be
		// able to access eclipse specific classes and methods.
	}

}

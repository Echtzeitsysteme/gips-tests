package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import gips.applyallnonzeromappings.connector.ApplyAllConnector;
import model.Root;
import model.SubstrateContainer;
import model.SubstrateResourceNode;

public class GipslAllBuildApplyAllMappingsTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ApplyAllConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testOneNode() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final SolverOutput ret = con.run("./" + OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		final Root model = loadModel();
		checkModel(model);
	}

	@Test
	public void testTwoNode() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 42);
		callableSetUp();

		final SolverOutput ret = con.run("./" + OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		final Root model = loadModel();
		checkModel(model);
	}

	@Test
	public void testTenNodes() {
		for (int i = 1; i <= 10; i++) {
			gen.genSubstrateNode("s" + i, i);
		}
		callableSetUp();

		final SolverOutput ret = con.run("./" + OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		final Root model = loadModel();
		checkModel(model);
	}

	@Test
	public void testZeroNodes() {
		callableSetUp();

		final SolverOutput ret = con.run("./" + OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		final Root model = loadModel();
		checkModel(model);
	}

	@Override
	public Class<?> getConnectorClass() {
		return ApplyAllConnector.class;
	}

	//
	// Utility methods
	//

	public static Root loadModel() {
		return loadModel("./" + OUTPUT_PATH);
	}

	public static Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		return (Root) res.getContents().get(0);
	}

	private static void checkModel(final Root model) {
		assertNotNull(model);
		assertNotNull(model.getContainers());
		assertFalse(model.getContainers().isEmpty());
		model.getContainers().forEach(c -> {
			if (c instanceof SubstrateContainer sc) {
				assertNotNull(sc.getSubstrateNodes());
				sc.getSubstrateNodes().forEach(snode -> {
					if (snode instanceof SubstrateResourceNode srnode) {
						assertEquals(0, srnode.getResourceAmountAvailable());
						assertEquals(0, srnode.getResourceAmountTotal());
					}
				});
			}
		});
	}

}

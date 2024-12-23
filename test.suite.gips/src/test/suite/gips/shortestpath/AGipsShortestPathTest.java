package test.suite.gips.shortestpath;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;

import genericgraphmetamodel.Edge;
import genericgraphmetamodel.Node;
import genericgraphmetamodel.Root;
import test.suite.gips.AbstractGipsTest;
import test.suite.gips.utils.AConnector;

public abstract class AGipsShortestPathTest extends AbstractGipsTest {

	protected static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	protected abstract void callableSetUp();

	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

	public void validatePath(final Integer[] expectedPath) {
		if (expectedPath == null || expectedPath.length == 0) {
			throw new IllegalArgumentException("Given expected path was null or empty.");
		}

		// Load GIPS solution XMI file
		final Root model = loadModel("./" + MODEL_PATH);
		Assert.assertNotNull(model);

		// Check model nodes
		Assert.assertNotNull(model.getNodes());
		Assert.assertFalse(model.getNodes().isEmpty());

		final Set<Node> filteredNodes = new HashSet<Node>();
		model.getNodes().forEach(n -> {
			for (int i = 0; i < expectedPath.length; i++) {
				if (n.getName() != null && n.getName().equals(String.valueOf(expectedPath[i]))) {
					filteredNodes.add(n);
				}
			}

		});

		Assert.assertEquals(expectedPath.length, filteredNodes.size());

		// Check model paths
		Assert.assertNotNull(model.getEdges());
		Assert.assertFalse(model.getEdges().isEmpty());

		final Set<Edge> expectedSelectedEdges = new HashSet<Edge>();
		final Set<Edge> expectedNotSelectedEdges = new HashSet<Edge>();

		model.getEdges().forEach(e -> {
			boolean found = false;
			for (int i = 0; i < expectedPath.length - 1; i++) {
				final String currentPathName = expectedPath[i] + "->" + expectedPath[i + 1];
				if (e.getName() != null && e.getName().equals(currentPathName)) {
					expectedSelectedEdges.add(e);
					found = true;
					break;
				}
			}
			if (!found) {
				expectedNotSelectedEdges.add(e);
			}
		});

		Assert.assertEquals(expectedPath.length - 1, expectedSelectedEdges.size());

		for (final Edge e : expectedSelectedEdges) {
			Assert.assertTrue(e.isSelected());
		}

		for (final Edge e : expectedNotSelectedEdges) {
			Assert.assertFalse(e.isSelected());
		}
	}

	public Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		return (Root) res.getContents().get(0);
	}

}

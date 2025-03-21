package genericgraphmetamodel.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import genericgraphmetamodel.Edge;
import genericgraphmetamodel.GenericgraphmetamodelFactory;
import genericgraphmetamodel.GenericgraphmetamodelPackage;
import genericgraphmetamodel.Node;
import genericgraphmetamodel.Root;

public class GenericGraphMetamodelGenerator {

	private Root model = null;

	public static void main(final String[] args) {
		new GenericGraphMetamodelGenerator().run(0, 4, "./instances/gen.xmi");
	}

	public void run(final int fromNode, final int toNode, final String outputPath) {
		initModel();

		for (int i = 0; i <= 8; i++) {
			genNode(String.valueOf(i));
		}

		genEdge("0", "1", 4);
		genEdge("0", "7", 8);

		genEdge("1", "0", 4);
		genEdge("1", "2", 8);
		genEdge("1", "7", 11);

		genEdge("2", "1", 8);
		genEdge("2", "3", 7);
		genEdge("2", "5", 4);
		genEdge("2", "8", 2);

		genEdge("3", "2", 7);
		genEdge("3", "4", 9);
		genEdge("3", "5", 14);

		genEdge("4", "3", 9);
		genEdge("4", "5", 10);

		genEdge("5", "2", 4);
		genEdge("5", "3", 14);
		genEdge("5", "4", 10);
		genEdge("5", "6", 2);

		genEdge("6", "5", 2);
		genEdge("6", "7", 1);
		genEdge("6", "8", 6);

		genEdge("7", "0", 8);
		genEdge("7", "1", 11);
		genEdge("7", "6", 1);
		genEdge("7", "8", 7);

		genEdge("8", "2", 2);
		genEdge("8", "6", 6);
		genEdge("8", "7", 7);

		propagateEdgesToNodes();

		model.setSourceNode(getNode(String.valueOf(fromNode)));
		model.setTargetNode(getNode(String.valueOf(toNode)));

		writeXmiToFile(outputPath, model);
	}

	private void initModel() {
		model = GenericgraphmetamodelFactory.eINSTANCE.createRoot();
	}

	private void genNode(final String name) {
		final Node node = GenericgraphmetamodelFactory.eINSTANCE.createNode();
		node.setName(name);
		model.getNodes().add(node);
	}

	private void genEdge(final String sourceNode, final String targetNode, final int capacity) {
		final Node source = getNode(sourceNode);
		final Node target = getNode(targetNode);
		final String name = sourceNode + "->" + targetNode;

		try {
			getEdge(name);
			throw new IllegalArgumentException();
		} catch (final UnsupportedOperationException ex) {
			// all good, edge is non-existing in model
		}

		final Edge edge = GenericgraphmetamodelFactory.eINSTANCE.createEdge();
		edge.setWeight(capacity);
		edge.setName(name);
		edge.setSource(source);
		edge.setTarget(target);

		model.getEdges().add(edge);
	}

	private Node getNode(final String name) {
		final Set<Node> filteredNodes = new HashSet<Node>();

		model.getNodes().forEach(n -> {
			if (n.getName() != null && n.getName().equals(name)) {
				filteredNodes.add(n);
			}
		});

		if (filteredNodes.size() != 1) {
			throw new UnsupportedOperationException();
		}

		return filteredNodes.iterator().next();
	}

	private Edge getEdge(final String name) {
		final Set<Edge> filteredEdges = new HashSet<Edge>();

		model.getEdges().forEach(e -> {
			if (e.getName() != null && e.getName().equals(name)) {
				filteredEdges.add(e);
			}
		});

		if (filteredEdges.size() != 1) {
			throw new UnsupportedOperationException();
		}

		return filteredEdges.iterator().next();
	}

	private void propagateEdgesToNodes() {
		model.getEdges().forEach(e -> {
			final String sourceName = e.getSource().getName();
			final String targetName = e.getTarget().getName();

			getNode(sourceName).getOutgoingEdges().add(e);
			getNode(targetName).getIncomingEdges().add(e);
		});
	}

	/**
	 * Writes the given model to an XMI file at the given file path.
	 * 
	 * @param path  File path to save the ResourceSet's contents to.
	 * @param model Model which should be saved to file.
	 */
	protected void writeXmiToFile(final String path, final Root model) {
		final URI uri = URI.createFileURI(path);
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl("../"));
		rs.getPackageRegistry().put(GenericgraphmetamodelPackage.eNS_URI, GenericgraphmetamodelPackage.eINSTANCE);
		final Resource r = rs.createResource(uri);
		r.getContents().add(model);
		try {
			r.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}

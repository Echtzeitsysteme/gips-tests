package gips.minvariablemodel.generator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import minvariablemodel.MinvariablemodelFactory;
import minvariablemodel.MinvariablemodelPackage;
import minvariablemodel.Root;

public class MinVariableModelGenerator {

	private Root model = null;

	private static MinVariableModelGenerator instance = new MinVariableModelGenerator();

	public void run(final Map<Integer, Integer> nodes, final List<Integer> contexts, final String outputPath) {
		initModel();

		nodes.forEach((k, v) -> {
			genNode(k, v);
		});
		contexts.forEach(c -> {
			genContextNode(c);
		});

		writeXmiToFile(outputPath, model);
	}

	public void initModel() {
		model = MinvariablemodelFactory.eINSTANCE.createRoot();
	}

	public void genNode(final int value, final int secondaryValue) {
		final var node = MinvariablemodelFactory.eINSTANCE.createNode();
		node.setSelected(false);
		node.setValueConstant(value);
		node.setValueSecondary(secondaryValue);
		this.model.getNodess().add(node);
	}

	public void genContextNode(final int contextValue) {
		final var node = MinvariablemodelFactory.eINSTANCE.createContext();
		node.setValueContext(contextValue);
		this.model.getContextNodes().add(node);
	}

	public static void reset() {
		MinVariableModelGenerator.instance.initModel();
	}

	public static MinVariableModelGenerator getInstance() {
		if (instance == null) {
			instance = new MinVariableModelGenerator();
		}
		return instance;
	}

	public void persistModel(final String path) {
		this.writeXmiToFile(path, model);
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
		rs.getPackageRegistry().put(MinvariablemodelPackage.eNS_URI, MinvariablemodelPackage.eINSTANCE);
		final Resource r = rs.createResource(uri);
		r.getContents().add(model);
		try {
			r.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}

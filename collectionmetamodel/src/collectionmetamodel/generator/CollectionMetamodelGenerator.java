package collectionmetamodel.generator;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import collectionmetamodel.CollectionmetamodelFactory;
import collectionmetamodel.CollectionmetamodelPackage;
import collectionmetamodel.Root;

public class CollectionMetamodelGenerator {

	private Root model = null;

	private static CollectionMetamodelGenerator instance = new CollectionMetamodelGenerator();

	public void initModel() {
		model = CollectionmetamodelFactory.eINSTANCE.createRoot();
	}

	public void genContainer() {
		final var container = CollectionmetamodelFactory.eINSTANCE.createContainer();
		this.model.getContainers().add(container);
	}

	public void genValue(final int value) {
		final var val = CollectionmetamodelFactory.eINSTANCE.createValueType();
		val.setVal(value);
		this.model.getContainers().get(0).getValueObjects().add(val);
	}

	public static void reset() {
		CollectionMetamodelGenerator.instance.initModel();
	}

	public static CollectionMetamodelGenerator getInstance() {
		if (instance == null) {
			instance = new CollectionMetamodelGenerator();
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
		rs.getPackageRegistry().put(CollectionmetamodelPackage.eNS_URI, CollectionmetamodelPackage.eINSTANCE);
		final Resource r = rs.createResource(uri);
		r.getContents().add(model);
		try {
			r.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}

package test.suite.gips.ilp.timeout;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.emoflon.smartemf.runtime.collections.LinkedSmartESet;

import timeoutmodel.Element;
import timeoutmodel.Root;
import timeoutmodel.Source;
import timeoutmodel.Target;
import timeoutmodel.TimeoutmodelFactory;

public class IlpTimeOutModelGenerator {

	private static Root root = TimeoutmodelFactory.eINSTANCE.createRoot();

	public static Root getRoot() {
		return root;
	}

	public static LinkedSmartESet<Element> getElements() {
		return root.getElements();
	}

	public static void reset() {
		root = TimeoutmodelFactory.eINSTANCE.createRoot();
	}

	public static void generateSrc(final String id, final int val) {
		final Source src = TimeoutmodelFactory.eINSTANCE.createSource();
		src.setId(id);
		src.setVal(val);
		getElements().add(src);
	}

	public static void generateTrg(final String id, final int val) {
		final Target trg = TimeoutmodelFactory.eINSTANCE.createTarget();
		trg.setId(id);
		trg.setVal(val);
		trg.setFree(val);
		getElements().add(trg);
	}

	public static Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		root = (Root) res.getContents().get(0);
		return root;
	}

	public static void persistModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		// Create new model for saving
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource r = rs.createResource(absPath);
		// Fetch model contents from eMoflon
		r.getContents().add(root);
		try {
			r.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}

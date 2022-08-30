package test.suite.gips.imports.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import importmodel.Element;
import importmodel.Guest;
import importmodel.Host;
import importmodel.ImportmodelFactory;
import importmodel.Root;

public class ImportsModelGenerator {

	private Root root;

	public ImportsModelGenerator() {
		root = ImportmodelFactory.eINSTANCE.createRoot();
	}

	public void persistModel(final String path) {
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

	public Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		root = (Root) res.getContents().get(0);
		return root;
	}

	public void genGuest(final String name, final int demand) {
		final Guest g = ImportmodelFactory.eINSTANCE.createGuest();
		g.setName(name);
		g.setDemand(demand);
		root.getElements().add(g);
	}

	public void genHost(final String name, final int resource) {
		final Host h = ImportmodelFactory.eINSTANCE.createHost();
		h.setName(name);
		h.setResource(resource);
		root.getElements().add(h);
	}

	public Element getElement(final String name) {
		for (final Element act : root.getElements()) {
			if (act.getName().equals(name)) {
				return act;
			}
		}
		return null;
	}

}

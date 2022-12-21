package test.suite.gips.multilayeredinheritence;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import multilayeredinheritencemodel.A;
import multilayeredinheritencemodel.B;
import multilayeredinheritencemodel.C;
import multilayeredinheritencemodel.MultilayeredinheritencemodelFactory;
import multilayeredinheritencemodel.Root;

public class MultiLayeredInheritenceModelGenerator extends AMultiLayeredInheritenceModelGenerator {

	private Root root = MultilayeredinheritencemodelFactory.eINSTANCE.createRoot();

	@Override
	public Root getRoot() {
		return root;
	}

	@Override
	public void reset() {
		root = MultilayeredinheritencemodelFactory.eINSTANCE.createRoot();
	}

	@Override
	public void generateA(final int id) {
		final A a = MultilayeredinheritencemodelFactory.eINSTANCE.createA();
		a.setIdA(id);
		getObjects().add(a);
	}

	@Override
	public void generateB(final int id) {
		final B b = MultilayeredinheritencemodelFactory.eINSTANCE.createB();
		b.setIdA(id);
		b.setIdB(id);
		getObjects().add(b);
	}

	@Override
	public void generateC(final int id) {
		final C c = MultilayeredinheritencemodelFactory.eINSTANCE.createC();
		c.setIdA(id);
		c.setIdB(id);
		c.setIdC(id);
		getObjects().add(c);
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

}

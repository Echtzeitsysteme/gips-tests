package test.suite.gips.multilayeredinheritence;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import model.ModelPackage;
import multilayeredinheritencemodel.A;
import multilayeredinheritencemodel.B;
import multilayeredinheritencemodel.C;
import multilayeredinheritencemodel.MultilayeredinheritencemodelFactory;
import multilayeredinheritencemodel.Root;

public class MultiLayeredInheritenceResourceSetModelGenerator {

	private static ResourceSet resourceSet = new ResourceSetImpl();

	public static Root getRoot() {
		return (Root) resourceSet.getResources().get(0).getContents().get(0);
	}

	public static void reset() {
		init();
	}

	public static Collection<A> getObjects() {
		return getRoot().getObjects();
	}

	public static void generateA(final int id) {
		final A a = MultilayeredinheritencemodelFactory.eINSTANCE.createA();
		a.setIdA(id);
		getObjects().add(a);
	}

	public static void generateB(final int id) {
		final B b = MultilayeredinheritencemodelFactory.eINSTANCE.createB();
		b.setIdA(id);
		b.setIdB(id);
		getObjects().add(b);
	}

	public static void generateC(final int id) {
		final C c = MultilayeredinheritencemodelFactory.eINSTANCE.createC();
		c.setIdA(id);
		c.setIdB(id);
		c.setIdC(id);
		getObjects().add(c);
	}

	private static void init() {
		resourceSet.getResources().clear();

		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl("../"));
		resourceSet.getPackageRegistry().put(ModelPackage.eINSTANCE.getNsURI(), ModelPackage.eINSTANCE);
		resourceSet.createResource(URI.createURI("model.xmi"));

		final Root root = MultilayeredinheritencemodelFactory.eINSTANCE.createRoot();

		resourceSet.getResources().get(0).getContents().add(root);
	}

	/**
	 * Returns the current model instance as resource set.
	 * 
	 * @return Current model instance as resource set.
	 */
	public static ResourceSet getResourceSet() {
		return resourceSet;
	}

}

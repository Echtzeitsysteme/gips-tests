package test.suite.gips.multilayeredinheritence;

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

public class MultiLayeredInheritenceResourceSetModelGenerator extends AMultiLayeredInheritenceModelGenerator {

	private ResourceSet resourceSet = new ResourceSetImpl();

	@Override
	public Root getRoot() {
		return (Root) resourceSet.getResources().get(0).getContents().get(0);
	}

	@Override
	public void reset() {
		clearResourceSet();
		createModel();
	}
	
	private void createModel() {
		final Root root = MultilayeredinheritencemodelFactory.eINSTANCE.createRoot();

		resourceSet.getResources().get(0).getContents().add(root);
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

	public void init() {
		clearResourceSet();

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
	public ResourceSet getResourceSet() {
		return resourceSet;
	}
	
	private void clearResourceSet() {
		resourceSet.getResources().forEach(r -> r.getContents().clear());
	}

}

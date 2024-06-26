package test.suite.gipsl.all.build.utils;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.emoflon.smartemf.runtime.collections.LinkedSmartESet;

import model.Container;
import model.ModelFactory;
import model.ModelPackage;
import model.Root;
import model.SubstrateContainer;
import model.SubstrateNode;
import model.SubstrateResourceNode;
import model.VirtualContainer;
import model.VirtualResourceNode;

public class AllBuildResourceSetModelGenerator {

	private final static String SUB_NAME = "sub";
	private final static String VIRT_NAME = "virt";
	private ResourceSet resourceSet = new ResourceSetImpl();

	public AllBuildResourceSetModelGenerator() {
		init();
	}

	private void init() {
		clearResourceSet();

		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl("../"));
		resourceSet.getPackageRegistry().put(ModelPackage.eINSTANCE.getNsURI(), ModelPackage.eINSTANCE);
		resourceSet.createResource(URI.createURI("model.xmi"));

		createModel();
	}

	private void createModel() {
		final Root root = ModelFactory.eINSTANCE.createRoot();
		final Container subCntr = ModelFactory.eINSTANCE.createSubstrateContainer();
		subCntr.setName(SUB_NAME);
		final Container virtCntr = ModelFactory.eINSTANCE.createVirtualContainer();
		virtCntr.setName(VIRT_NAME);
		root.getContainers().add(subCntr);
		root.getContainers().add(virtCntr);

		resourceSet.getResources().get(0).getContents().add(root);
	}

	public void genVirtualNode(final String name, final int resourceDemand) {
		final VirtualResourceNode node = ModelFactory.eINSTANCE.createVirtualResourceNode();
		node.setName(name);
		node.setResourceDemand(resourceDemand);
		((VirtualContainer) getContainer(VIRT_NAME)).getVirtualNodes().add(node);
	}

	public void genSubstrateNode(final String name, final int resourceAmountTotal) {
		final SubstrateResourceNode node = ModelFactory.eINSTANCE.createSubstrateResourceNode();
		node.setName(name);
		node.setResourceAmountTotal(resourceAmountTotal);
		node.setResourceAmountAvailable(resourceAmountTotal);
		((SubstrateContainer) getContainer(SUB_NAME)).getSubstrateNodes().add(node);
	}

	public void deleteSubstrateNode(final String name) {
		int index = 0;
		final LinkedSmartESet<SubstrateNode> snodes = ((SubstrateContainer) getContainer(SUB_NAME)).getSubstrateNodes();
		final Iterator<SubstrateNode> it = snodes.iterator();
		while (it.hasNext()) {
			final SubstrateNode sn = it.next();
			if (sn.getName() != null && sn.getName().equals(name)) {
				break;
			}
			index++;
		}
		snodes.remove(index);
	}

	public Container getContainer(final String name) {
		final Root root = getRoot();
		for (final Container act : root.getContainers()) {
			if (act.getName().equals(name)) {
				return act;
			}
		}
		return null;
	}

	/**
	 * Returns the current model instance as resource set.
	 * 
	 * @return Current model instance as resource set.
	 */
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	/**
	 * Returns the root node.
	 *
	 * @return Root node.
	 */
	public Root getRoot() {
		return (Root) resourceSet.getResources().get(0).getContents().get(0);
	}

	public void reset() {
		clearResourceSet();
		createModel();
	}

	private void clearResourceSet() {
		resourceSet.getResources().forEach(r -> r.getContents().clear());
	}

}

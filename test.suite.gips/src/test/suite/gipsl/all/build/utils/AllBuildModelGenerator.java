package test.suite.gipsl.all.build.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import model.Container;
import model.ModelFactory;
import model.Root;
import model.SubstrateContainer;
import model.SubstrateResourceNode;
import model.VirtualContainer;
import model.VirtualResourceNode;

public class AllBuildModelGenerator {

	private Root root;
	private final static String SUB_NAME = "sub";
	private final static String VIRT_NAME = "virt";

	public AllBuildModelGenerator() {
		root = ModelFactory.eINSTANCE.createRoot();
		final Container subCntr = ModelFactory.eINSTANCE.createSubstrateContainer();
		subCntr.setName(SUB_NAME);
		final Container virtCntr = ModelFactory.eINSTANCE.createVirtualContainer();
		virtCntr.setName(VIRT_NAME);
		root.getContainers().add(subCntr);
		root.getContainers().add(virtCntr);
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
		((SubstrateContainer) getContainer(SUB_NAME)).getSubstrateNodes().add(node);
	}

	public Container getContainer(final String name) {
		for (final Container act : root.getContainers()) {
			if (act.getName().equals(name)) {
				return act;
			}
		}
		return null;
	}

}

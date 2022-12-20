package gipsl.all.build.resourcesetinit.connector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourcesetinit.api.gips.ResourcesetinitGipsAPI;
import model.Container;
import model.ModelFactory;
import model.ModelPackage;
import model.Root;
import model.SubstrateContainer;
import model.SubstrateResourceNode;
import model.VirtualContainer;
import model.VirtualResourceNode;

public class ResourceInitManualTest {

	protected GipsEngineAPI<?, ?> api;

	final ResourceSet resourceSet = new ResourceSetImpl();

	// Tests for debugging

	@Test
	public void map2to1Default() {
		init();
		
		// Create model nodes
		genSubstrateNode("s1", 2);
		genVirtualNode("v1", 1);
		genVirtualNode("v2", 1);

		// Create API
		api = new ResourcesetinitGipsAPI();
		api.init(resourceSet);

		api.buildILPProblem(true);

		// Test
		final ILPSolverOutput ret = api.solveILPProblem();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());
	}
	
	@Test
	public void map2to1AfterApiInit() {
		init();

		// Create API
		api = new ResourcesetinitGipsAPI();
		api.init(resourceSet);
		
		// Create model nodes
		genSubstrateNode("s1", 2);
		genVirtualNode("v1", 1);
		genVirtualNode("v2", 1);
		
		final ResourceSet apiRs = api.getResourceSet();
		final ResourceSet eApiRs = api.getEMoflonAPI().getModel();
		final ResourceSet eAppRs = api.getEMoflonApp().getModel();

		api.buildILPProblem(true);

		// Test
		final ILPSolverOutput ret = api.solveILPProblem();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());
	}

	// Utilities

	private void init() {
		// Create a resource set with a root node
		resourceSet.getResources().clear();

		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl("../"));
		resourceSet.getPackageRegistry().put(ModelPackage.eINSTANCE.getNsURI(), ModelPackage.eINSTANCE);
		resourceSet.createResource(URI.createURI("model.xmi"));

		final Root root = ModelFactory.eINSTANCE.createRoot();
		final Container subCntr = ModelFactory.eINSTANCE.createSubstrateContainer();
		subCntr.setName("sub");
		final Container virtCntr = ModelFactory.eINSTANCE.createVirtualContainer();
		virtCntr.setName("virt");
		root.getContainers().add(subCntr);
		root.getContainers().add(virtCntr);

		resourceSet.getResources().get(0).getContents().add(root);
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

	public Root getRoot() {
		return (Root) resourceSet.getResources().get(0).getContents().get(0);
	}

	public void genVirtualNode(final String name, final int resourceDemand) {
		final VirtualResourceNode node = ModelFactory.eINSTANCE.createVirtualResourceNode();
		node.setName(name);
		node.setResourceDemand(resourceDemand);
		((VirtualContainer) getContainer("virt")).getVirtualNodes().add(node);
	}

	public void genSubstrateNode(final String name, final int resourceAmountTotal) {
		final SubstrateResourceNode node = ModelFactory.eINSTANCE.createSubstrateResourceNode();
		node.setName(name);
		node.setResourceAmountTotal(resourceAmountTotal);
		node.setResourceAmountAvailable(resourceAmountTotal);
		((SubstrateContainer) getContainer("sub")).getSubstrateNodes().add(node);
	}

}

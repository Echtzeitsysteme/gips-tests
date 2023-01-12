package gipsl.all.build.resourcesetinit.connector;

import org.eclipse.emf.ecore.resource.ResourceSet;

import gipsl.all.build.resourcesetinit.api.gips.ResourcesetinitGipsAPI;
import test.suite.gips.utils.AResourceConnector;
import test.suite.gips.utils.GlobalTestConfig;

public class RsinitConnector extends AResourceConnector {

	public RsinitConnector(final ResourceSet model) {
		api = new ResourcesetinitGipsAPI();
		api.init(model);
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public void apply() {
		((ResourcesetinitGipsAPI) api).getN2n().applyNonZeroMappings();
	}

}

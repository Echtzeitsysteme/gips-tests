package gips.multilayeredinheritencersinit.connector;

import org.eclipse.emf.ecore.resource.ResourceSet;

import gips.multilayeredinheritencersinit.api.gips.MultilayeredinheritencersinitGipsAPI;
import test.suite.gips.utils.AResourceConnector;
import test.suite.gips.utils.GlobalTestConfig;

public class MultiLayeredInheritenceRSInitConnector extends AResourceConnector {

	public MultiLayeredInheritenceRSInitConnector(final ResourceSet model) {
		api = new MultilayeredinheritencersinitGipsAPI();
		api.init(model);
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public void apply() {
		((MultilayeredinheritencersinitGipsAPI) api).getZa().applyNonZeroMappings();
	}

}

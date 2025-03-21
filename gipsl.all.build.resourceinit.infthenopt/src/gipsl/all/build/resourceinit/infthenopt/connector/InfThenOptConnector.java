package gipsl.all.build.resourceinit.infthenopt.connector;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.resourceinit.infthenopt.api.gips.InfthenoptGipsAPI;
import test.suite.gips.utils.AResourceConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class InfThenOptConnector extends AResourceConnector {

	public InfThenOptConnector(final String modelPath) {
		api = new InfthenoptGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((InfthenoptGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public InfThenOptConnector(final ResourceSet model) {
		api = new InfthenoptGipsAPI();
		api.init(model);
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public void apply() {
		((InfthenoptGipsAPI) api).getN2n().applyNonZeroMappings();
	}

	public ResourceSet getResourceSet() {
		return api.getResourceSet();
	}

}

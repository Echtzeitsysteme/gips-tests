package gipsl.imports.manyrefs.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.imports.manyrefs.api.gips.ManyrefsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ManyRefsConnector extends AConnector {

	public ManyRefsConnector(final String modelPath) {
		api = new ManyrefsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		getAPI().getGuest2host().applyNonZeroMappings();
		getAPI().applyAllBoundVariables();
		return output;
	}

	public ManyrefsGipsAPI getAPI() {
		return (ManyrefsGipsAPI) api;
	}

}

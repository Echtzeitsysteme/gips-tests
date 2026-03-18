package gipsl.all.build.manyrefs.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.manyrefs.api.gips.ManyrefsGipsAPI;
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
		((ManyrefsGipsAPI) api).getN2n().applyNonZeroMappings();
		((ManyrefsGipsAPI) api).applyAllBoundVariables();
		return output;
	}

}

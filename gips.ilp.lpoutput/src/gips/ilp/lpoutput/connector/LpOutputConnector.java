package gips.ilp.lpoutput.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.ilp.lpoutput.api.gips.LpoutputGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class LpOutputConnector extends AConnector {

	public LpOutputConnector(final String modelPath) {
		api = new LpoutputGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((LpoutputGipsAPI) api).getS2t().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

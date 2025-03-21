package gipsl.all.build.simple.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.simple.api.gips.SimpleGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SimpleConnector extends AConnector {

	public SimpleConnector(final String modelPath) {
		api = new SimpleGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SimpleGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.and.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.and.api.gips.AndGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class AndConnector extends AConnector {

	public AndConnector(final String modelPath) {
		api = new AndGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((AndGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.multiplepatternaccesses.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.multiplepatternaccesses.api.gips.MultiplepatternaccessesGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MultiplePatternAccessesConnector extends AConnector {

	public MultiplePatternAccessesConnector(final String modelPath) {
		api = new MultiplepatternaccessesGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((MultiplepatternaccessesGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

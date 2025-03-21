package gipsl.all.build.implication.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.implication.api.gips.ImplicationGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ImplicationConnector extends AConnector {

	public ImplicationConnector(final String modelPath) {
		api = new ImplicationGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(String outputPath) {
		final SolverOutput output = solve();
		((ImplicationGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

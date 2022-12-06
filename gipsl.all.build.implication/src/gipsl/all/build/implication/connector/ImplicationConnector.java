package gipsl.all.build.implication.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(String outputPath) {
		final ILPSolverOutput output = solve();
		((ImplicationGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

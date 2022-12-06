package gipsl.all.build.objective.max.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.objective.max.api.gips.MaxGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveMaxConnector extends AConnector {

	public ObjectiveMaxConnector(final String modelPath) {
		api = new MaxGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((MaxGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

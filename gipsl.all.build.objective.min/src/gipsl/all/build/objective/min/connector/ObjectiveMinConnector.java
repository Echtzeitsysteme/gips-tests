package gipsl.all.build.objective.min.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.objective.min.api.gips.MinGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveMinConnector extends AConnector {

	public ObjectiveMinConnector(final String modelPath) {
		api = new MinGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((MinGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

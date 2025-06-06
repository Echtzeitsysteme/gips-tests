package gipsl.all.build.objective.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.objective.api.gips.ObjectiveGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveConnector extends AConnector {

	public ObjectiveConnector(final String modelPath) {
		api = new ObjectiveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ObjectiveGipsAPI) api).getA().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

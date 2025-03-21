package gipsl.all.build.objective.scaling.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.objective.scaling.api.gips.ScalingGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveScalingConnector extends AConnector {

	public ObjectiveScalingConnector(final String modelPath) {
		api = new ScalingGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ScalingGipsAPI) api).getA().applyNonZeroMappings();
		((ScalingGipsAPI) api).getB().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

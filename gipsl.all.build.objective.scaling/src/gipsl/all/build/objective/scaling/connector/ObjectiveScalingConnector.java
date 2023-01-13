package gipsl.all.build.objective.scaling.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ScalingGipsAPI) api).getA().applyNonZeroMappings();
		((ScalingGipsAPI) api).getB().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

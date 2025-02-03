package gips.generic.scheduling.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.generic.scheduling.api.gips.SchedulingGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class GenericSchedulingConnector extends AConnector {

	public GenericSchedulingConnector(final String modelPath) {
		api = new SchedulingGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		final var ams = ((SchedulingGipsAPI) api).getT2s().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

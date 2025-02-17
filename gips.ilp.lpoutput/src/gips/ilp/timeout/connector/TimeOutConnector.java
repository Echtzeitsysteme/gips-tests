package gips.ilp.timeout.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.ilp.timeout.gips.TimeoutHiPEGipsApi;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class TimeOutConnector extends AConnector {

	public TimeOutConnector(final String modelPath) {
		api = new TimeoutHiPEGipsApi();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((TimeoutHiPEGipsApi) api).getS2t().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

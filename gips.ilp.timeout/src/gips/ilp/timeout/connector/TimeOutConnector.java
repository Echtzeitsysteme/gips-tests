package gips.ilp.timeout.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.ilp.timeout.api.gips.TimeoutGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class TimeOutConnector extends AConnector {

	public TimeOutConnector(final String modelPath) {
		api = new TimeoutGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((TimeoutGipsAPI) api).getS2t().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

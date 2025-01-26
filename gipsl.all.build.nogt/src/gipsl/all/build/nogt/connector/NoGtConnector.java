package gipsl.all.build.nogt.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.nogt.api.gips.NogtGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class NoGtConnector extends AConnector {

	public NoGtConnector(final String modelPath) {
		api = new NogtGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		// No matches/mappers to apply
		return output;
	}

}

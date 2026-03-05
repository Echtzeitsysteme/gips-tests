package gips.constraints.autoremove.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.constraints.autoremove.api.gips.AutoremoveGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class AutoremoveConnector extends AConnector {

	public AutoremoveConnector(final String modelPath) {
		api = new AutoremoveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((AutoremoveGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

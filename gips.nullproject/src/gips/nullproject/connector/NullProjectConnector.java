package gips.nullproject.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.nullproject.api.gips.NullprojectGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class NullProjectConnector extends AConnector {

	public NullProjectConnector(final String modelPath) {
		api = new NullprojectGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((NullprojectGipsAPI) api).getAsn().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

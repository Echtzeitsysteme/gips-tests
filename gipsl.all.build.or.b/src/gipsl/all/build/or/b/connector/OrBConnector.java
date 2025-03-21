package gipsl.all.build.or.b.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.or.b.api.gips.BGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class OrBConnector extends AConnector {

	public OrBConnector(final String modelPath) {
		api = new BGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((BGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.or.a.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.or.a.api.gips.AGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class OrAConnector extends AConnector {

	public OrAConnector(final String modelPath) {
		api = new AGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((AGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

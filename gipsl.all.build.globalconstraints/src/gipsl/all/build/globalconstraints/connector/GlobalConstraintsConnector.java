package gipsl.all.build.globalconstraints.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.globalconstraints.api.gips.GlobalconstraintsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class GlobalConstraintsConnector extends AConnector {

	public GlobalConstraintsConnector(final String modelPath) {
		api = new GlobalconstraintsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		return output;
	}

}

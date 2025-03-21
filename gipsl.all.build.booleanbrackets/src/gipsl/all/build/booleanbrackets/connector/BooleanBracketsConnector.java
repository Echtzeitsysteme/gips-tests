package gipsl.all.build.booleanbrackets.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.booleanbrackets.api.gips.BooleanbracketsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class BooleanBracketsConnector extends AConnector {

	public BooleanBracketsConnector(final String modelPath) {
		api = new BooleanbracketsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		return output;
	}

}

package gipsl.all.build.boolexpr.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.boolexpr.api.gips.BoolexprGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class BoolExprConnector extends AConnector {

	public BoolExprConnector(final String modelPath) {
		api = new BoolexprGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		return output;
	}

}

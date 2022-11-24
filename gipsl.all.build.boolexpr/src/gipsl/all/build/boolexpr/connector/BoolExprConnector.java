package gipsl.all.build.boolexpr.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.boolexpr.api.gips.BoolexprGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class BoolExprConnector extends AConnector {

	public BoolExprConnector(final String modelPath) {
		api = new BoolexprGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		return output;
	}

}

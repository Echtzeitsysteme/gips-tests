package gipsl.all.build.booleanbrackets.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.booleanbrackets.api.gips.BooleanbracketsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class BooleanBracketsConnector extends AConnector {

	public BooleanBracketsConnector(final String modelPath) {
		api = new BooleanbracketsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		return output;
	}

}

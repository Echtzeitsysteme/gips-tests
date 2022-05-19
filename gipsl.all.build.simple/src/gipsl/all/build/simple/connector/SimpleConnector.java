package gipsl.all.build.simple.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.simple.api.gips.SimpleGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class SimpleConnector extends AConnector {

	public SimpleConnector(final String modelPath) {
		api = new SimpleGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SimpleGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

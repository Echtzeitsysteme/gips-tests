package gipsl.all.build.implicationstatic.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.implicationstatic.api.gips.ImplicationstaticGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class ImplicationStaticConnector extends AConnector {

	public ImplicationStaticConnector(final String modelPath) {
		api = new ImplicationstaticGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		// No mappings/matches to apply
		return output;
	}

}

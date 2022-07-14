package gipsl.all.build.not.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.not.api.gips.NotGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class NotConnector extends AConnector {

	public NotConnector(final String modelPath) {
		api = new NotGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((NotGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

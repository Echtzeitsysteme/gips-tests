package gipsl.all.build.notempty.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.notempty.api.gips.NotemptyGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class NotEmptyConnector extends AConnector {

	public NotEmptyConnector(final String modelPath) {
		api = new NotemptyGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((NotemptyGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.notor.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.notor.api.gips.NotorGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class NotOrConnector extends AConnector {

	public NotOrConnector(final String modelPath) {
		api = new NotorGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((NotorGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

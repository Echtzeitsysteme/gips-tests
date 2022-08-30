package gipsl.imports.sub.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.imports.sub.api.gips.SubGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class ImportsSubConnector extends AConnector {

	public ImportsSubConnector(final String modelPath) {
		api = new SubGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SubGipsAPI) api).getG2h().applyNonZeroMappings();
		return output;
	}

}

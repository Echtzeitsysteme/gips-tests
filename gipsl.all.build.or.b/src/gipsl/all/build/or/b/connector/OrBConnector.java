package gipsl.all.build.or.b.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.or.b.api.gips.BGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class OrBConnector extends AConnector {

	public OrBConnector(final String modelPath) {
		api = new BGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((BGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.or.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.or.a.api.gips.AGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class OrAConnector extends AConnector {

	public OrAConnector(final String modelPath) {
		api = new AGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((AGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

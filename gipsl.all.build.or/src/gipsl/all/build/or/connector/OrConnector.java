package gipsl.all.build.or.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.or.api.gips.OrGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class OrConnector extends AConnector {

	public OrConnector(final String modelPath) {
		api = new OrGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((OrGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

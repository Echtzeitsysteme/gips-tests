package gips.tsp.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.tsp.api.gips.TspGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class TspConnector extends AConnector {

	public TspConnector(final String modelPath) {
		api = new TspGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((TspGipsAPI) api).getC2c().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

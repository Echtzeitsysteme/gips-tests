package gips.tsp.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.tsp.api.gips.TspGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class TspConnector extends AConnector {

	public TspConnector(final String modelPath) {
		api = new TspGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((TspGipsAPI) api).getC2c().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

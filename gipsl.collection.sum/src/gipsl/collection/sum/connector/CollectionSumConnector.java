package gipsl.collection.sum.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.collection.sum.api.gips.SumGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class CollectionSumConnector extends AConnector {

	public CollectionSumConnector(final String modelPath) {
		api = new SumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		// There is no mapping to apply.
		return output;
	}

}

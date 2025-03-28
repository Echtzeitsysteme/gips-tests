package gipsl.all.build.contexttimestwo.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.contexttimestwo.api.gips.ContexttimestwoGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ContextTimesTwoConnector extends AConnector {

	public ContextTimesTwoConnector(final String modelPath) {
		api = new ContexttimestwoGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ContexttimestwoGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

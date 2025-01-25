package gips.enumequals.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.enumequals.api.gips.EnumequalsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class EnumEqualsConnector extends AConnector {

	public EnumEqualsConnector(final String modelPath) {
		api = new EnumequalsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((EnumequalsGipsAPI) api).getM().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

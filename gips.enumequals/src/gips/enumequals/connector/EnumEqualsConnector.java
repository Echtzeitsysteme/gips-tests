package gips.enumequals.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((EnumequalsGipsAPI) api).getM().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

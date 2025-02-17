package gips.enumequals.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

import gips.enumequals.gips.EnumequalsHiPEGipsApi;

public class EnumEqualsConnector extends AConnector {

	public EnumEqualsConnector(final String modelPath) {
		api = new EnumequalsHiPEGipsApi();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((EnumequalsHiPEGipsApi) api).getM().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

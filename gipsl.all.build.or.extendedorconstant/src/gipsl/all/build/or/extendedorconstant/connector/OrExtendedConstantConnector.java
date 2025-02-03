package gipsl.all.build.or.extendedorconstant.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.or.extendedorconstant.api.gips.ExtendedorconstantGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class OrExtendedConstantConnector extends AConnector {

	public OrExtendedConstantConnector(final String modelPath) {
		api = new ExtendedorconstantGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ExtendedorconstantGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

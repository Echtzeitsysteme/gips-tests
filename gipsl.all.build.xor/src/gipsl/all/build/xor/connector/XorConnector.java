package gipsl.all.build.xor.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.xor.api.gips.XorGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class XorConnector extends AConnector {

	public XorConnector(final String modelPath) {
		api = new XorGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((XorGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

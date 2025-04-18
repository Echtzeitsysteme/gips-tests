package gipsl.all.build.implicationstatic.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.implicationstatic.api.gips.ImplicationstaticGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ImplicationStaticConnector extends AConnector {

	public ImplicationStaticConnector(final String modelPath) {
		api = new ImplicationstaticGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		// No mappings/matches to apply
		return output;
	}

}

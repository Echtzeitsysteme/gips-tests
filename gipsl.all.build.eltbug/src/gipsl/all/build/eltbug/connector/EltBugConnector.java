package gipsl.all.build.eltbug.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.eltbug.api.gips.EltbugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class EltBugConnector extends AConnector {

	public EltBugConnector(final String modelPath) {
		api = new EltbugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((EltbugGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

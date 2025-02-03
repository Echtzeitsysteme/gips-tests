package gipsl.all.build.varlimit.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varlimit.api.gips.VarlimitGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarLimitConnector extends AConnector {

	public VarLimitConnector(final String modelPath) {
		api = new VarlimitGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((VarlimitGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

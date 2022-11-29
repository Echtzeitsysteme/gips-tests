package gipsl.all.build.varlimit.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarlimitGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

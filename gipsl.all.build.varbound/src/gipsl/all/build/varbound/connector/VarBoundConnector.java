package gipsl.all.build.varbound.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varbound.api.gips.VarboundGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarBoundConnector extends AConnector {

	public VarBoundConnector(final String modelPath) {
		api = new VarboundGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public String getLPOutputPath() {
		return api.getSolverConfig().getLpPath();
	}

	public VarboundGipsAPI getAPI() {
		return (VarboundGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
//		((VarboundGipsAPI) api).getN2n().applyNonZeroMappings();
//		save(outputPath);
		return output;
	}

}

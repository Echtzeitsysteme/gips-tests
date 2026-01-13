package gipsl.all.build.typeextension.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.typeextension.api.gips.TypeextensionGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class TypeextensionConnector extends AConnector {

	public TypeextensionConnector(final String modelPath) {
		api = new TypeextensionGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public String getLPOutputPath() {
		return api.getSolverConfig().getLpPath();
	}

	public TypeextensionGipsAPI getAPI() {
		return (TypeextensionGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
//		((TypeextensionGipsAPI) api).getN2n().applyNonZeroMappings();
//		save(outputPath);
		return output;
	}

}

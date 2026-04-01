package gipsl.imports.typeselect.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.imports.typeselect.api.gips.TypeselectGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class TypeselectConnector extends AConnector {

	public TypeselectConnector(final String modelPath) {
		api = new TypeselectGipsAPI();
		GlobalTestConfig.overrideSolver(api);
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	public String getLPOutputPath() {
		return api.getSolverConfig().getLpPath();
	}

	public TypeselectGipsAPI getAPI() {
		return (TypeselectGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		return output;
	}

}

package gipsl.imports.scalarattributes.setexpressions.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.imports.scalarattributes.setexpressions.api.gips.SetexpressionsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SetExpressionsConnector extends AConnector {

	public SetExpressionsConnector(final String modelPath) {
		api = new SetexpressionsGipsAPI();
		GlobalTestConfig.overrideSolver(api);
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	public SetexpressionsGipsAPI getAPI() {
		return (SetexpressionsGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		return output;
	}

}

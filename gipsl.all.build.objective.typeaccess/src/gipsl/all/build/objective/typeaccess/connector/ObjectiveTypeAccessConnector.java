package gipsl.all.build.objective.typeaccess.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.objective.typeaccess.api.gips.TypeaccessGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveTypeAccessConnector extends AConnector {

	public ObjectiveTypeAccessConnector(final String modelPath) {
		api = new TypeaccessGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((TypeaccessGipsAPI) api).getA().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

package gipsl.all.build.objectivemapping.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.objectivemapping.api.gips.ObjectivemappingGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveMappingConnector extends AConnector {

	public ObjectiveMappingConnector(final String modelPath) {
		api = new ObjectivemappingGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ObjectivemappingGipsAPI) api).getA().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

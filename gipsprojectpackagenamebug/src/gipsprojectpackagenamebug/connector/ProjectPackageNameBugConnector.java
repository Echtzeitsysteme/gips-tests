package gipsprojectpackagenamebug.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsprojectpackagenamebug.api.gips.GipsprojectpackagenamebugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ProjectPackageNameBugConnector extends AConnector {

	public ProjectPackageNameBugConnector(final String modelPath) {
		api = new GipsprojectpackagenamebugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((GipsprojectpackagenamebugGipsAPI) api).getAsn().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

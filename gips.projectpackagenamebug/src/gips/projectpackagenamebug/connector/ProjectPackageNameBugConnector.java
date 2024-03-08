package gips.projectpackagenamebug.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.projectpackagenamebug.api.gips.ProjectpackagenamebugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ProjectPackageNameBugConnector extends AConnector {

	public ProjectPackageNameBugConnector(final String modelPath) {
		api = new ProjectpackagenamebugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ProjectpackagenamebugGipsAPI) api).getAsn().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

package gips.projectpacketnambug.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.projectpacketnamebug.api.gips.ProjectpacketnamebugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ProjectPacketNameBugConnector extends AConnector {

	public ProjectPacketNameBugConnector(final String modelPath) {
		api = new ProjectpacketnamebugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ProjectpacketnamebugGipsAPI) api).getAsn().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

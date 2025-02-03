package gips.launchconfig.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.launchconfig.api.gips.LaunchconfigGipsAPI;
import test.suite.gips.utils.AConnector;

public class LaunchConfigConnector extends AConnector {

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((LaunchconfigGipsAPI) api).getMapNode().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

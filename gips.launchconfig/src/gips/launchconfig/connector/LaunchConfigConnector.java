package gips.launchconfig.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.launchconfig.api.gips.LaunchconfigGipsAPI;
import test.suite.gips.utils.AConnector;

public class LaunchConfigConnector extends AConnector {

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((LaunchconfigGipsAPI) api).getMapNode().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

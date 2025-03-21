package gips.sort.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.sort.api.gips.SortGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SortConnector extends AConnector {

	public SortConnector(final String modelPath) {
		api = new SortGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SortGipsAPI) api).getE2e().applyNonZeroMappings();
		((SortGipsAPI) api).getE2self().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

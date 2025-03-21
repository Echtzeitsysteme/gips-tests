package shortestpath.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import shortestpath.api.gips.ShortestpathGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ShortestPathConnector extends AConnector {

	public ShortestPathConnector(final String modelPath) {
		api = new ShortestpathGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ShortestpathGipsAPI) api).getSelectEdgeMapping().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

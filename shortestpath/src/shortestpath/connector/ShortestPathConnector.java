package shortestpath.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ShortestpathGipsAPI) api).getSelectEdge().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

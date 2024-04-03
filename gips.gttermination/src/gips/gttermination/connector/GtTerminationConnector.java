package gips.gttermination.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

import gips.gttermination.api.gips.GtterminationGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class GtTerminationConnector extends AConnector {

	public GtTerminationConnector(final String modelPath) {
		api = new GtterminationGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((GtterminationGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

	public GraphTransformationAPI getEmoflonApi() {
		return api.getEMoflonAPI();
	}

}

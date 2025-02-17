package gips.gttermination.connector;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.ibex.gt.api.IBeXGtAPI;

import gips.gttermination.gips.GtterminationHiPEGipsApi;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class GtTerminationConnector extends AConnector {

	public GtTerminationConnector(final String modelPath) {
		api = new GtterminationHiPEGipsApi();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((GtterminationHiPEGipsApi) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

	public IBeXGtAPI<?, ?, ?> getEmoflonApi() {
		return api.getEMoflonAPI();
	}

}

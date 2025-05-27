package gips.applyallnonzeromappings.connector;

import java.io.IOException;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.applyallnonzeromappings.api.gips.ApplyallnonzeromappingsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ApplyAllConnector extends AConnector {

	public ApplyAllConnector(final String modelPath) {
		api = new ApplyallnonzeromappingsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ApplyallnonzeromappingsGipsAPI) api).applyAllNonZeroMappings();
		try {
			api.saveResult(outputPath);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return output;
	}

}

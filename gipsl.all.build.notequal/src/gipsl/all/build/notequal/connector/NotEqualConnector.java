package gipsl.all.build.notequal.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.notequal.api.gips.NotequalGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class NotEqualConnector extends AConnector {

	public NotEqualConnector(final String modelPath) {
		api = new NotequalGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((NotequalGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

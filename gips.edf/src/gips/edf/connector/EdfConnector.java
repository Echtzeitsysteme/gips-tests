package gips.edf.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.edf.api.gips.EdfGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class EdfConnector extends AConnector {

	public EdfConnector(final String modelPath) {
		api = new EdfGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		final var ams = ((EdfGipsAPI) api).getT2s().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

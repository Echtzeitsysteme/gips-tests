package gipsl.string.compare.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.string.compare.api.gips.CompareGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class StringCompareConnector extends AConnector {

	public StringCompareConnector(final String modelPath) {
		api = new CompareGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((CompareGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

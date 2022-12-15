package gipsl.string.compare.filter.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.string.compare.filter.api.gips.FilterGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class StringCompareFilterConnector extends AConnector {

	public StringCompareFilterConnector(final String modelPath) {
		api = new FilterGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((FilterGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

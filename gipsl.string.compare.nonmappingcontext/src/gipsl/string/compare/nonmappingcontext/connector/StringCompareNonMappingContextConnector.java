package gipsl.string.compare.nonmappingcontext.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.string.compare.nonmappingcontext.api.gips.NonmappingcontextGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class StringCompareNonMappingContextConnector extends AConnector {

	public StringCompareNonMappingContextConnector(final String modelPath) {
		api = new NonmappingcontextGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((NonmappingcontextGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

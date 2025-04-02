package gipsl.string.compare.nonmappingcontext.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.string.compare.nonmappingcontext.api.gips.NonmappingcontextGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class StringCompareNonMappingContextConnector extends AConnector {

	public StringCompareNonMappingContextConnector(final String modelPath) {
		api = new NonmappingcontextGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((NonmappingcontextGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

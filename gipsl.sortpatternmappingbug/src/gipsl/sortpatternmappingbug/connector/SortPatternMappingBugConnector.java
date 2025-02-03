package gipsl.sortpatternmappingbug.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.sortpatternmappingbug.api.gips.SortpatternmappingbugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SortPatternMappingBugConnector extends AConnector {

	public SortPatternMappingBugConnector(final String modelPath) {
		api = new SortpatternmappingbugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SortpatternmappingbugGipsAPI) api).getMa().applyNonZeroMappings();
		((SortpatternmappingbugGipsAPI) api).getMb().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

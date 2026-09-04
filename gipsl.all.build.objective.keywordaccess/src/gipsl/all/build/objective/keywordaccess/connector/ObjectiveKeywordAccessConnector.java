package gipsl.all.build.objective.keywordaccess.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.objective.keywordaccess.api.gips.KeywordaccessGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjectiveKeywordAccessConnector extends AConnector {

	public ObjectiveKeywordAccessConnector(final String modelPath) {
		api = new KeywordaccessGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((KeywordaccessGipsAPI) api).getA().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

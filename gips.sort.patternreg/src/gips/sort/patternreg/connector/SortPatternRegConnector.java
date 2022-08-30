package gips.sort.patternreg.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.sort.patternreg.api.gips.PatternregGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class SortPatternRegConnector extends AConnector {

	public SortPatternRegConnector(final String modelPath) {
		api = new PatternregGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		solve();
		return null;
	}

}

package gips.sort.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gips.sort.api.gips.SortGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class SortConnector extends AConnector {

	public SortConnector(final String modelPath) {
		api = new SortGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SortGipsAPI) api).getE2e().applyNonZeroMappings();
		((SortGipsAPI) api).getE2self().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

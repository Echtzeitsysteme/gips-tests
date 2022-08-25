package gipsl.all.build.count.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.count.api.gips.CountGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class CountConnector extends AConnector {

	public CountConnector(final String modelPath) {
		api = new CountGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((CountGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

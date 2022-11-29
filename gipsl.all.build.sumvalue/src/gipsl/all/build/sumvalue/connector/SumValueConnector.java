package gipsl.all.build.sumvalue.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.sumvalue.api.gips.SumvalueGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SumValueConnector extends AConnector {

	public SumValueConnector(final String modelPath) {
		api = new SumvalueGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SumvalueGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

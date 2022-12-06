package gipsl.all.build.sumvalueinf.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.sumvalueinf.api.gips.SumvalueinfGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SumValueInfConnector extends AConnector {

	public SumValueInfConnector(final String modelPath) {
		api = new SumvalueinfGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SumvalueinfGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

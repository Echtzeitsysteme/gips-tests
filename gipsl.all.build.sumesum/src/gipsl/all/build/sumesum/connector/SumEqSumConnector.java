package gipsl.all.build.sumesum.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.sumesum.api.gips.SumesumGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SumEqSumConnector extends AConnector {

	public SumEqSumConnector(final String modelPath) {
		api = new SumesumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SumesumGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

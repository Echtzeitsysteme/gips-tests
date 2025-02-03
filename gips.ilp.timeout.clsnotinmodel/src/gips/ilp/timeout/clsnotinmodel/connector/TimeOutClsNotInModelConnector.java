package gips.ilp.timeout.clsnotinmodel.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.ilp.timeout.clsnotinmodel.api.gips.ClsnotinmodelGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class TimeOutClsNotInModelConnector extends AConnector {

	public TimeOutClsNotInModelConnector(final String modelPath) {
		api = new ClsnotinmodelGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ClsnotinmodelGipsAPI) api).getS2t().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

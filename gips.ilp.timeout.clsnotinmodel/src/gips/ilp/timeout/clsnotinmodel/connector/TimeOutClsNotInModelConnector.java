package gips.ilp.timeout.clsnotinmodel.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ClsnotinmodelGipsAPI) api).getS2t().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

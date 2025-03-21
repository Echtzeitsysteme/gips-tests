package gipsl.all.build.equals.fxeqctrafo.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.equals.fxeqctrafo.api.gips.FxeqctrafoGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class FxeqctrafoConnector extends AConnector {

	public FxeqctrafoConnector(final String modelPath) {
		api = new FxeqctrafoGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		// Do not apply the mappings on purpose
//		((FxeqctrafoGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public int getNumberOfNonZeroN2n() {
		return ((FxeqctrafoGipsAPI) api).getN2n().getNonZeroVariableMappings().size();
	}

	public int getNumberOfNonZeroMu() {
		return ((FxeqctrafoGipsAPI) api).getMu().getNonZeroVariableMappings().size();
	}

}

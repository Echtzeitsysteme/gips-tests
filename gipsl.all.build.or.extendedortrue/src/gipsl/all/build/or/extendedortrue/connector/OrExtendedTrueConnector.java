package gipsl.all.build.or.extendedortrue.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.or.extendedortrue.api.gips.ExtendedortrueGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class OrExtendedTrueConnector extends AConnector {

	public OrExtendedTrueConnector(final String modelPath) {
		api = new ExtendedortrueGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ExtendedortrueGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

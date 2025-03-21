package gipsl.all.build.filter.serial.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.filter.serial.api.gips.SerialGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class FilterSerialConnector extends AConnector {

	public FilterSerialConnector(final String modelPath) {
		api = new SerialGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SerialGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

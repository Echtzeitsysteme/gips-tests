package gips.multilayeredinheritence.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.multilayeredinheritence.api.gips.MultilayeredinheritenceGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MultiLayeredInheritenceConnector extends AConnector {

	public MultiLayeredInheritenceConnector(final String modelPath) {
		api = new MultilayeredinheritenceGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((MultilayeredinheritenceGipsAPI) api).getZa().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

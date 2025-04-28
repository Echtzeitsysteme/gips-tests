package gips.minvariable.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.minvariable.api.gips.MinvariableGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MinVariableConnector extends AConnector {

	public MinVariableConnector(final String modelPath) {
		api = new MinvariableGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((MinvariableGipsAPI) api).getSelect().applyNonZeroMappings();
		return output;
	}

	public int getMinVarValue() {
		final var mappings = ((MinvariableGipsAPI) api).getMinimum().getMappings().values();

		if (mappings.size() != 1) {
			throw new InternalError();
		}

		for (final var m : mappings) {
			return m.getMinimum().getValue();
		}
		return -1;
	}

}

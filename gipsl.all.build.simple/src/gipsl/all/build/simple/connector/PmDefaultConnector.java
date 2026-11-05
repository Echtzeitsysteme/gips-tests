package gipsl.all.build.simple.connector;

import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.simple.api.gips.SimpleGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

/**
 * This connector explicitly overrides the `solve()` method to use the default
 * value for the `update` parameter of the GIPS framework.
 */
public class PmDefaultConnector extends AConnector {

	public PmDefaultConnector(final String modelPath) {
		api = new SimpleGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.override(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SimpleGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	@Override
	protected SolverOutput solve() {
		// Build the ILP problem (including **default** updates)
		api.buildProblem();
		return api.solveProblem();
	}

	public int getNumberOfSelectedMappings() {
		int selectedMappings = 0;
		for (final GipsMapper<?> m : api.getMappers().values()) {
			selectedMappings += m.getNonZeroVariableMappings().size();
		}
		return selectedMappings;
	}

}

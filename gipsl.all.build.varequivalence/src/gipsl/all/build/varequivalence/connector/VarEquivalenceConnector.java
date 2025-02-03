package gipsl.all.build.varequivalence.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varequivalence.api.gips.VarequivalenceGipsAPI;
import gipsl.all.build.varequivalence.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarEquivalenceConnector extends AConnector {

	public VarEquivalenceConnector(final String modelPath) {
		api = new VarequivalenceGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((VarequivalenceGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarequivalenceGipsAPI) api).getN2n().getMappings();
	}

}

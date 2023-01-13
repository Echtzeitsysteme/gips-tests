package gipsl.all.build.varsobjective.connector;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.varsobjective.api.gips.VarsobjectiveGipsAPI;
import gipsl.all.build.varsobjective.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsObjectiveConnector extends AConnector {

	public VarsObjectiveConnector(final String modelPath) {
		api = new VarsobjectiveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarsobjectiveGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarsobjectiveGipsAPI) api).getN2n().getMappings();
	}

}

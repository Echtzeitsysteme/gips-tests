package gipsl.all.build.vars.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.vars.api.gips.VarsGipsAPI;
import gipsl.all.build.vars.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsConnector extends AConnector {

	public VarsConnector(final String modelPath) {
		api = new VarsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((VarsGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarsGipsAPI) api).getN2n().getMappings();
	}

}

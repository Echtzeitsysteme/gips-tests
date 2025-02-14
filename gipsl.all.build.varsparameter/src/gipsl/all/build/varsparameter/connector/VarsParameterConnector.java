package gipsl.all.build.varsparameter.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varsparameter.api.gips.VarsparameterGipsAPI;
import gipsl.all.build.varsparameter.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsParameterConnector extends AConnector {

	public VarsParameterConnector(final String modelPath) {
		api = new VarsparameterGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((VarsparameterGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarsparameterGipsAPI) api).getN2n().getMappings();
	}

}

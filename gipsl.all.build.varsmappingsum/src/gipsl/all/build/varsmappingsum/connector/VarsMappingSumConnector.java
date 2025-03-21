package gipsl.all.build.varsmappingsum.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varsmappingsum.api.gips.VarsmappingsumGipsAPI;
import gipsl.all.build.varsmappingsum.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsMappingSumConnector extends AConnector {

	public VarsMappingSumConnector(final String modelPath) {
		api = new VarsmappingsumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((VarsmappingsumGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarsmappingsumGipsAPI) api).getN2n().getMappings();
	}

}

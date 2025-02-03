package gipsl.all.build.varmappingsum.sumfreevarmapping.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varmappingsum.sumfreevarmapping.api.gips.SumfreevarmappingGipsAPI;
import gipsl.all.build.varmappingsum.sumfreevarmapping.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsMappingSumFreeVarMappingConnector extends AConnector {

	public VarsMappingSumFreeVarMappingConnector(final String modelPath) {
		api = new SumfreevarmappingGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SumfreevarmappingGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((SumfreevarmappingGipsAPI) api).getN2n().getMappings();
	}

}

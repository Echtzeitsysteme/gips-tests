package gipsl.all.build.varsmappingsum.sumfreevarmult.connector;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.varmappingsum.sumfreevarmult.api.gips.SumfreevarmultGipsAPI;
import gipsl.all.build.varmappingsum.sumfreevarmult.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsMappingSumFreeVarMultConnector extends AConnector {

	public VarsMappingSumFreeVarMultConnector(final String modelPath) {
		api = new SumfreevarmultGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SumfreevarmultGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((SumfreevarmultGipsAPI) api).getN2n().getMappings();
	}

}

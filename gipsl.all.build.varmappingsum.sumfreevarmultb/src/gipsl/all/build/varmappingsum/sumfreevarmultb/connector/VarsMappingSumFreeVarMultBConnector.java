package gipsl.all.build.varmappingsum.sumfreevarmultb.connector;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.varmappingsum.sumfreevarmultb.api.gips.SumfreevarmultbGipsAPI;
import gipsl.all.build.varmappingsum.sumfreevarmultb.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsMappingSumFreeVarMultBConnector extends AConnector {

	public VarsMappingSumFreeVarMultBConnector(final String modelPath) {
		api = new SumfreevarmultbGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SumfreevarmultbGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((SumfreevarmultbGipsAPI) api).getN2n().getMappings();
	}

}

package gipsl.all.build.varmappingsum.sumfreevar.connector;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.varmappingsum.sumfreevar.api.gips.SumfreevarGipsAPI;
import gipsl.all.build.varmappingsum.sumfreevar.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsMappingSumFreeVarConnector extends AConnector {

	public VarsMappingSumFreeVarConnector(final String modelPath) {
		api = new SumfreevarGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SumfreevarGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((SumfreevarGipsAPI) api).getN2n().getMappings();
	}

}

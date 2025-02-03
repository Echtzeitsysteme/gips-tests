package gipsl.all.build.varmappingsum.sumfreevarattr.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varmappingsum.sumfreevarattr.api.gips.SumfreevarattrGipsAPI;
import gipsl.all.build.varmappingsum.sumfreevarattr.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsMappingSumFreeVarAttrConnector extends AConnector {

	public VarsMappingSumFreeVarAttrConnector(final String modelPath) {
		api = new SumfreevarattrGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SumfreevarattrGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((SumfreevarattrGipsAPI) api).getN2n().getMappings();
	}

}

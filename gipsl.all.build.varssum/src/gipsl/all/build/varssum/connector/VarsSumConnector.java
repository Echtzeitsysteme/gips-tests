package gipsl.all.build.varssum.connector;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.varssum.api.gips.VarssumGipsAPI;
import gipsl.all.build.varssum.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsSumConnector extends AConnector {

	public VarsSumConnector(final String modelPath) {
		api = new VarssumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarssumGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarssumGipsAPI) api).getN2n().getMappings();
	}

}

package gipsl.all.build.varsbooleanbug.connector;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.varsbooleanbug.api.gips.VarsbooleanbugGipsAPI;
import gipsl.all.build.varsbooleanbug.api.gips.mapping.DummyMMapping;
import gipsl.all.build.varsbooleanbug.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarsBooleanBugConnector extends AConnector {

	public VarsBooleanBugConnector(final String modelPath) {
		api = new VarsbooleanbugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((VarsbooleanbugGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}
	
	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarsbooleanbugGipsAPI) api).getN2n().getMappings();
	}
	
	public Map<String, DummyMMapping> getDummyMMappings() {
		return ((VarsbooleanbugGipsAPI) api).getDummyM().getMappings();
	}

}

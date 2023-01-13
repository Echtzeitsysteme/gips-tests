package gipsl.all.build.vardoubleimpl.connector;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.vardoubleimpl.api.gips.VardoubleimplGipsAPI;
import gipsl.all.build.vardoubleimpl.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class VarDoubleImplConnector extends AConnector {

	public VarDoubleImplConnector(final String modelPath) {
		api = new VardoubleimplGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VardoubleimplGipsAPI) api).getN2n().applyNonZeroMappings();

		save(outputPath);
		return output;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VardoubleimplGipsAPI) api).getN2n().getMappings();
	}

}

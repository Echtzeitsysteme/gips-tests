package gipsl.all.build.typevarnpe.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.typevarnpe.api.gips.TypevarnpeGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class TypeVarNpeConnector extends AConnector {

	public TypeVarNpeConnector(final String modelPath) {
		api = new TypevarnpeGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((TypevarnpeGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

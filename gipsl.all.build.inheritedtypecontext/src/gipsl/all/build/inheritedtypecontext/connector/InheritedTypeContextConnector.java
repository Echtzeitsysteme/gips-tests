package gipsl.all.build.inheritedtypecontext.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.inheritedtypecontext.api.gips.InheritedtypecontextGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class InheritedTypeContextConnector extends AConnector {

	public InheritedTypeContextConnector(final String modelPath) {
		api = new InheritedtypecontextGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		// No mappings/matches to apply
		return output;
	}

}

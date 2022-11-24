package gipsl.all.build.inheritedtypecontext.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.inheritedtypecontext.api.gips.InheritedtypecontextGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class InheritedTypeContextConnector extends AConnector {

	public InheritedTypeContextConnector(final String modelPath) {
		api = new InheritedtypecontextGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		// No mappings/matches to apply
		return output;
	}

}

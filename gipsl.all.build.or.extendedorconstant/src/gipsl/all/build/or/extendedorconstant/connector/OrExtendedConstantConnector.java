package gipsl.all.build.or.extendedorconstant.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.or.extendedorconstant.api.gips.ExtendedorconstantGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class OrExtendedConstantConnector extends AConnector {

	public OrExtendedConstantConnector(final String modelPath) {
		api = new ExtendedorconstantGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ExtendedorconstantGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

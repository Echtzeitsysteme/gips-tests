package gipsl.all.build.xor.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.xor.api.gips.XorGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class XorConnector extends AConnector {

	public XorConnector(final String modelPath) {
		api = new XorGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((XorGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

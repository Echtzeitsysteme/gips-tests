package gipsl.all.build.or.extendedortrue.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.or.extendedortrue.api.gips.ExtendedortrueGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class OrExtendedTrueConnector extends AConnector {

	public OrExtendedTrueConnector(final String modelPath) {
		api = new ExtendedortrueGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ExtendedortrueGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.or.extendedor.tru.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.or.extendedor.api.gips.ExtendedorGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class OrExtendedTrueConnector extends AConnector {

	public OrExtendedTrueConnector(final String modelPath) {
		api = new ExtendedorGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ExtendedorGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

package gipsl.all.build.sumesum.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.sumesum.api.gips.SumesumGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class SumEqSumConnector extends AConnector {

	public SumEqSumConnector(final String modelPath) {
		api = new SumesumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SumesumGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

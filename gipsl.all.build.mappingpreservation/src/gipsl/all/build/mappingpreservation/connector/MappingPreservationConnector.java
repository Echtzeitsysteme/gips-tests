package gipsl.all.build.mappingpreservation.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.mappingpreservation.api.gips.MappingpreservationGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MappingPreservationConnector extends AConnector {

	public MappingPreservationConnector(final String modelPath) {
		api = new MappingpreservationGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((MappingpreservationGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public ILPSolverOutput runWithUpdates() {
		final ILPSolverOutput output = solve();
		((MappingpreservationGipsAPI) api).getN2n().applyNonZeroMappings(true);
		return output;
	}

}

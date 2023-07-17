package gipsl.all.build.mappingpreservation.connector;

import java.util.function.Function;

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
//		((MappingpreservationGipsAPI) api).getN2n().getMappings().forEach((k,v) -> {
//			v.getValue();
//		});
		((MappingpreservationGipsAPI) api).getN2n().applyMappings(new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(final Integer t) {
				return true;
			}
		}, true);
		return output;
	}

	public ILPSolverOutput runWithNoApplication(final String outputPath) {
		final ILPSolverOutput output = solve();
		save(outputPath);
		return output;
	}

	public void applyMapping(final int id) {
		((MappingpreservationGipsAPI) api).getN2n().applyMappings(new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(final Integer t) {
				return t == id;
			}
		}, true);
	}

//	@Override
//	public void save(final String path) {
//		((MappingpreservationGipsAPI) api).saveResult(path);
//	}

}

package gips.all.build.objective.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.objective.api.gips.ObjectiveGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class ObjectiveConnector extends AConnector {

	public ObjectiveConnector(final String modelPath) {
		api = new ObjectiveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((ObjectiveGipsAPI) api).getA().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

package gips.all.build.objective.connector;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.objective.api.gips.ObjectiveGipsAPI;
import test.suite.gips.utils.GipsTestUtils;

public class ObjectiveConnector {

	final ObjectiveGipsAPI api;

	public ObjectiveConnector(final String modelPath) {
		api = new ObjectiveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	public ILPSolverOutput run(final String outputPath) {
		final URI absPath = GipsTestUtils.pathToAbsUri(outputPath);

		// Build the ILP problem (including updates)
		api.buildILPProblem(true);
		final ILPSolverOutput output = api.solveILPProblem();
		System.out.println("Solver status: " + output.status());
		System.out.println("Objective value: " + output.objectiveValue());

		api.getA().applyNonZeroMappings();

		try {
			api.saveResult(absPath.toFileString());
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return output;
	}

}

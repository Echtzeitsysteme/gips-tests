package gipsl.all.build.simple.connector;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.simple.api.gips.SimpleGipsAPI;
import test.suite.gips.utils.GipsTestUtils;

public class Connector {
	final SimpleGipsAPI api;

	public Connector(final String modelPath) {
		api = new SimpleGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	public ILPSolverOutput run(final String outputPath) {
		final URI absPath = GipsTestUtils.pathToAbsUri(outputPath);

		// Build the ILP problem (including updates)
		api.buildILPProblem(true);
		final ILPSolverOutput output = api.solveILPProblem();
		System.out.println("Solver status: " + output.status());
		System.out.println("Objective value: " + output.objectiveValue());

		api.getN2n().applyNonZeroMappings();

		try {
			api.saveResult(absPath.toFileString());
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return output;
	}

}

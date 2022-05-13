package gipsl.all.build.connector;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.api.gips.BuildGipsAPI;

public class Connector {
	final BuildGipsAPI api;

	public Connector(final String modelPath) {
		if (modelPath == null || modelPath.isBlank()) {
			throw new IllegalArgumentException("Model path is invalid!");
		}

		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + modelPath);

		api = new BuildGipsAPI();
		api.init(absPath);
	}

	public void run(final String outputPath) {
		if (outputPath == null || outputPath.isBlank()) {
			throw new IllegalArgumentException("Output path is invalid!");
		}

		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + outputPath);

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
	}

}

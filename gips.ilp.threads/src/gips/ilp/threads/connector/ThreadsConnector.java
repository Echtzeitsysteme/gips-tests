package gips.ilp.threads.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.ilp.threads.api.gips.ThreadsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ThreadsConnector extends AConnector {

	public ThreadsConnector(final String modelPath, final int newNumberOfThreads) {
		this(modelPath);
		api.setIlpSolverThreads(newNumberOfThreads);
	}

	public ThreadsConnector(final String modelPath) {
		api = new ThreadsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ThreadsGipsAPI) api).getS2t().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

	/*
	 * Setters for the test implementation.
	 */

	public void setSolverConfigThreads(final int value) {
		api.setIlpSolverThreads(value);
	}

	/*
	 * Getters for the test implementation.
	 */

	public int getSolverConfigThreads() {
		return api.getSolverConfig().threads();
	}

	public boolean getSolverConfigThreadsEnabled() {
		return api.getSolverConfig().threadCount();
	}

}

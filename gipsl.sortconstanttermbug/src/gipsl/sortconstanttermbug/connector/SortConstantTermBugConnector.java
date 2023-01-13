package gipsl.sortconstanttermbug.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.sortconstanttermbug.api.gips.SortconstanttermbugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SortConstantTermBugConnector extends AConnector {

	public SortConstantTermBugConnector(final String modelPath) {
		api = new SortconstanttermbugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((SortconstanttermbugGipsAPI) api).getMa().applyNonZeroMappings();
		return output;
	}

}

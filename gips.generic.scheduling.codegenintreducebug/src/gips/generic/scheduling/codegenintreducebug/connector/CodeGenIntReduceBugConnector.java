package gips.generic.scheduling.codegenintreducebug.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.generic.scheduling.codegenintreducebug.api.gips.CodegenintreducebugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class CodeGenIntReduceBugConnector extends AConnector {

	public CodeGenIntReduceBugConnector(final String modelPath) {
		api = new CodegenintreducebugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		final var ams = ((CodegenintreducebugGipsAPI) api).getT2s().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

package gips.generic.scheduling.codegenintreducebug.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		final var ams = ((CodegenintreducebugGipsAPI) api).getT2s().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}

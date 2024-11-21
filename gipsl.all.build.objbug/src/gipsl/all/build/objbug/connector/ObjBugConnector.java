package gipsl.all.build.objbug.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.objbug.api.gips.ObjbugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjBugConnector extends AConnector {

	public ObjBugConnector(final String modelPath) {
		api = new ObjbugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
//		((ObjbugGipsAPI) api).getM().getNonZeroVariableMappings();
		save(outputPath);
		return output;
	}

}

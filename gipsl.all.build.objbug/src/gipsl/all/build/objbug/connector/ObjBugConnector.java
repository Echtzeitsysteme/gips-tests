package gipsl.all.build.objbug.connector;

import org.emoflon.gips.core.milp.SolverOutput;

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
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
//		((ObjbugGipsAPI) api).getM().getNonZeroVariableMappings();
		save(outputPath);
		return output;
	}

}

package gipsl.all.build.objbugb.connector;


import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.objbugb.api.gips.ObjbugbGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ObjBugBConnector extends AConnector {

	public ObjBugBConnector(final String modelPath) {
		api = new ObjbugbGipsAPI();
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

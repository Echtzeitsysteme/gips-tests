package org.gipsl.all.build.nestedattributeaccess.connector;

import org.emoflon.gips.core.milp.SolverOutput;
import org.gipsl.all.build.nestedattributeaccess.api.gips.NestedattributeaccessGipsAPI;

import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class NestedAttributeAccessConnector extends AConnector {

	public NestedAttributeAccessConnector(final String modelPath) {
		api = new NestedattributeaccessGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((NestedattributeaccessGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}

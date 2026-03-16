package gipsl.all.build.uppercasenames.connector;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

import gipsl.all.build.uppercasenames.api.gips.UppercasenamesGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class UpperCaseNamesConnector extends AConnector {

	public UpperCaseNamesConnector(final String modelPath) {
		api = new UppercasenamesGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((UppercasenamesGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}
	
	public GraphTransformationAPI getEmoflonApi() {
		return api.getEMoflonAPI();
	}

}

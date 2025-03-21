package gipsl.all.build.resourcesinit.optthenvallog.connector;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.resourcesinit.optthenvallog.api.gips.OptthenvallogGipsAPI;
import test.suite.gips.utils.AResourceConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class OptThenValidationLogConnector extends AResourceConnector {

	public OptThenValidationLogConnector(final String modelPath) {
		api = new OptthenvallogGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((OptthenvallogGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public OptThenValidationLogConnector(final ResourceSet model) {
		api = new OptthenvallogGipsAPI();
		api.init(model);
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public void apply() {
		((OptthenvallogGipsAPI) api).getN2n().applyNonZeroMappings();
	}

}

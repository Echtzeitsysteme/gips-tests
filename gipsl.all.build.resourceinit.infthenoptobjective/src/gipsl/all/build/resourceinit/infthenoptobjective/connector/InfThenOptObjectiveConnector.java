package gipsl.all.build.resourceinit.infthenoptobjective.connector;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.resourceinit.infthenoptobjective.api.gips.InfthenoptobjectiveGipsAPI;
import test.suite.gips.utils.AResourceConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class InfThenOptObjectiveConnector extends AResourceConnector {

	public InfThenOptObjectiveConnector(final String modelPath) {
		api = new InfthenoptobjectiveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((InfthenoptobjectiveGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public InfThenOptObjectiveConnector(final ResourceSet model) {
		api = new InfthenoptobjectiveGipsAPI();
		api.init(model);
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public void apply() {
		((InfthenoptobjectiveGipsAPI) api).getN2n().applyNonZeroMappings();
	}

	public ResourceSet getResourceSet() {
		return api.getResourceSet();
	}

}

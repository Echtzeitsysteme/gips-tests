package gipsl.all.build.varsobjective.connector;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPVariable;

import gipsl.all.build.varsobjective.api.gips.VarsobjectiveGipsAPI;
import gipsl.all.build.varsobjective.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.VarsOutput;

public class VarsObjectiveConnector extends AConnector {

	private VarsOutput varsOutput;

	public VarsObjectiveConnector(final String modelPath) {
		api = new VarsobjectiveGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		varsOutput = new VarsOutput(null, new HashMap<String, ILPVariable<?>>(), new HashMap<String, ILPVariable<?>>());
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarsobjectiveGipsAPI) api).getN2n().applyNonZeroMappings();

		// Get all bound variables and get all free variables
		varsOutput = new VarsOutput(output, new HashMap<String, ILPVariable<?>>(),
				new HashMap<String, ILPVariable<?>>());
		((VarsobjectiveGipsAPI) api).getN2n().getMappings().forEach((key, mapping) -> {
			mapping.getBoundVariables().forEach((varName, var) -> {
				varsOutput.boundVars().put(varName, var);
			});
			mapping.getFreeVariables().forEach((varName, var) -> {
				varsOutput.freeVars().put(varName, var);
			});
		});

		save(outputPath);
		return output;
	}

	public VarsOutput getVarsOutput() {
		return varsOutput;
	}

	public Map<String, N2nMapping> getN2nMappings() {
		return ((VarsobjectiveGipsAPI) api).getN2n().getMappings();
	}

}

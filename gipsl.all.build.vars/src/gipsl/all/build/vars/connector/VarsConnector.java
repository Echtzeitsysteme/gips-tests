package gipsl.all.build.vars.connector;

import java.util.HashMap;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPVariable;

import gipsl.all.build.vars.api.gips.VarsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;

public class VarsConnector extends AConnector {
	
	private VarsOutput varsOutput;

	public VarsConnector(final String modelPath) {
		api = new VarsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		varsOutput = new VarsOutput(null, new HashMap<String, ILPVariable<?>>(), new HashMap<String, ILPVariable<?>>());
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarsGipsAPI) api).getN2n().applyNonZeroMappings();
		
		// Get all bound variables and get all free variables
		varsOutput = new VarsOutput(output, new HashMap<String, ILPVariable<?>>(), new HashMap<String, ILPVariable<?>>());
		((VarsGipsAPI) api).getN2n().getMappings().forEach((key, mapping) -> {
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

}

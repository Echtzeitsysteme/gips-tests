package gipsl.all.build.varssum.connector;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPVariable;

import gipsl.all.build.varssum.api.gips.VarssumGipsAPI;
import gipsl.all.build.varssum.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.VarsOutput;

public class VarsSumConnector extends AConnector {

	private VarsOutput varsOutput;

	public VarsSumConnector(final String modelPath) {
		api = new VarssumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		varsOutput = new VarsOutput(null, new HashMap<String, ILPVariable<?>>(), new HashMap<String, ILPVariable<?>>());
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarssumGipsAPI) api).getN2n().applyNonZeroMappings();

		// Get all bound variables and get all free variables
		varsOutput = new VarsOutput(output, new HashMap<String, ILPVariable<?>>(),
				new HashMap<String, ILPVariable<?>>());
		((VarssumGipsAPI) api).getN2n().getMappings().forEach((key, mapping) -> {
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
		return ((VarssumGipsAPI) api).getN2n().getMappings();
	}

}

package gipsl.all.build.varsmappingsum.connector;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPVariable;

import gipsl.all.build.varsmappingsum.api.gips.VarsmappingsumGipsAPI;
import gipsl.all.build.varsmappingsum.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.VarsOutput;

public class VarsMappingSumConnector extends AConnector {

	private VarsOutput varsOutput;

	public VarsMappingSumConnector(final String modelPath) {
		api = new VarsmappingsumGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		varsOutput = new VarsOutput(null, new HashMap<String, ILPVariable<?>>(), new HashMap<String, ILPVariable<?>>());
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((VarsmappingsumGipsAPI) api).getN2n().applyNonZeroMappings();

		// Get all bound variables and get all free variables
		varsOutput = new VarsOutput(output, new HashMap<String, ILPVariable<?>>(),
				new HashMap<String, ILPVariable<?>>());
		((VarsmappingsumGipsAPI) api).getN2n().getMappings().forEach((key, mapping) -> {
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
		return ((VarsmappingsumGipsAPI) api).getN2n().getMappings();
	}

}

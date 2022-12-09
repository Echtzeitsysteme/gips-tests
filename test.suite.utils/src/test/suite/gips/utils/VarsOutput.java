package test.suite.gips.utils;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPVariable;

public record VarsOutput(ILPSolverOutput ilpSolverOutput, Map<String, ILPVariable<?>> boundVars, Map<String, ILPVariable<?>> freeVars) {
		
}

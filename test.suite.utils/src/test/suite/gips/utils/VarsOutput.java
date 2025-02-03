package test.suite.gips.utils;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.model.Variable;

public record VarsOutput(SolverOutput ilpSolverOutput, Map<String, Variable<?>> boundVars,
		Map<String, Variable<?>> freeVars) {

}

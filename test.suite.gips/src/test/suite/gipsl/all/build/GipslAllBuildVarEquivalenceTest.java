package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.gt.GTMapping;

import gipsl.all.build.varequivalence.connector.VarEquivalenceConnector;

public class GipslAllBuildVarEquivalenceTest extends AGipslAllBuildVarEqMappingTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarEquivalenceConnector(MODEL_PATH);
	}

	@Override
	protected void runChecks(final boolean exptectedZero) {
		assertFalse(((VarEquivalenceConnector) con).getN2nMappings().isEmpty());
		final Map<String, GTMapping<?, ?>> mappings = new HashMap<>();
		((VarEquivalenceConnector) con).getN2nMappings().forEach((k, v) -> {
			mappings.put(k, v);
		});
		checkConstraints(mappings, exptectedZero);
	}

	@Override
	public Class<?> getConnectorClass() {
		return VarEquivalenceConnector.class;
	}

}

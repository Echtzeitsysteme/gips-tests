package test.suite.gips.multilayeredinheritence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;

import gips.multilayeredinheritencersinit.connector.MultiLayeredInheritenceRSInitConnector;
import test.suite.gips.utils.AResourceConnector;

public class MultiLayeredInheritenceRSInitTest extends AMultiLayeredInheritenceTest {

	// Overwrite the parent AConnector field
	protected AResourceConnector con;

	@Override
	protected void runAndVerify(int objDesVal) {
		if (objDesVal < 0) {
			throw new IllegalArgumentException("Desired objective value < 0.");
		}

		final SolverOutput ret = con.solve();
		con.apply();
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(objDesVal, Math.abs(ret.objectiveValue()));
		assertFalse(ret.validationLog().isNotValid());
	}

	@Override
	@BeforeEach
	public void resetModel() {
		gen = new MultiLayeredInheritenceResourceSetModelGenerator();
		((MultiLayeredInheritenceResourceSetModelGenerator) gen).init();
		gen.reset();
		final ResourceSet model = ((MultiLayeredInheritenceResourceSetModelGenerator) gen).getResourceSet();
		con = new MultiLayeredInheritenceRSInitConnector(model);
	}

	@Override
	public Class<?> getConnectorClass() {
		return MultiLayeredInheritenceRSInitConnector.class;
	}

}

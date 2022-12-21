package test.suite.gips.multilayeredinheritence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
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

		final ILPSolverOutput ret = con.solve();
		con.apply();
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(objDesVal, ret.objectiveValue());
		assertFalse(ret.validationLog().isNotValid());
	}

	@Override
	@BeforeEach
	public void resetModel() {
		gen = new MultiLayeredInheritenceResourceSetModelGenerator();
		gen.reset();
		final ResourceSet model = ((MultiLayeredInheritenceResourceSetModelGenerator) gen).getResourceSet();
		con = new MultiLayeredInheritenceRSInitConnector(model);
	}

}

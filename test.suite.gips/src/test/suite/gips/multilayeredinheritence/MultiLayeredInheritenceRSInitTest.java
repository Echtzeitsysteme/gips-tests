package test.suite.gips.multilayeredinheritence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;

import gips.multilayeredinheritencersinit.connector.MultiLayeredInheritenceRSInitConnector;
import test.suite.gips.utils.AResourceConnector;

public class MultiLayeredInheritenceRSInitTest extends MultiLayeredInheritenceTest {

	protected AResourceConnector con;

	@BeforeEach
	public void setUp() {
		MultiLayeredInheritenceResourceSetModelGenerator.reset();
		final ResourceSet model = MultiLayeredInheritenceResourceSetModelGenerator.getResourceSet();
		con = new MultiLayeredInheritenceRSInitConnector(model);
	}

	// Utilities

	@Override
	protected void runAndyVerify(final int objDesVal) {
		if (objDesVal < 0) {
			throw new IllegalArgumentException("Desired objective value < 0.");
		}

		final ILPSolverOutput ret = con.solve();
		con.apply();
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(objDesVal, ret.objectiveValue());
		assertFalse(ret.validationLog().isNotValid());
	}

}

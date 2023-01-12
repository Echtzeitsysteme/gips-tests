package test.suite.gips.multilayeredinheritence;

import java.util.Collection;

import multilayeredinheritencemodel.A;
import multilayeredinheritencemodel.Root;

public abstract class AMultiLayeredInheritenceModelGenerator {

	public abstract Root getRoot();

	public abstract void reset();

	public Collection<A> getObjects() {
		return getRoot().getObjects();
	}

	public abstract void generateA(final int id);

	public abstract void generateB(final int id);

	public abstract void generateC(final int id);

}

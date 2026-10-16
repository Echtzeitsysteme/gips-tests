package gipsl.all.build.implicitboolean.connector;

import java.util.Objects;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.implicitboolean.api.gips.ImplicitbooleanGipsAPI;
import model.VirtualResourceNode;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class ImplicitBooleanConnector extends AConnector {

	public ImplicitBooleanConnector(final String modelPath) {
		api = new ImplicitbooleanGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.override(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ImplicitbooleanGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public int getVirtualResourceNodeS(final String name) {
		Objects.requireNonNull(name);
		return getVirtualResourceNode("s", name);
	}

	public int getVirtualResourceNodeX(final String name) {
		Objects.requireNonNull(name);
		return getVirtualResourceNode("x", name);
	}

	private int getVirtualResourceNode(final String var, final String name) {
		Objects.requireNonNull(var);
		Objects.requireNonNull(name);
		final var t = api.getTypeExtensions()
				.get("platform:/resource/gipsl.all.build.model/model/Model.ecore/VirtualResourceNode");

		for (final var e : t.getExtensions()) {
			if (((VirtualResourceNode) e.getContext()).getName().equals(name)) {
				return (int) e.getVariables().get(var).getValue();
			}
		}
		throw new UnsupportedOperationException();
	}

}

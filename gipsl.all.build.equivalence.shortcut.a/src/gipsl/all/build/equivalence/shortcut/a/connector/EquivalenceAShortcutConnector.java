package gipsl.all.build.equivalence.shortcut.a.connector;

import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.equivalence.shortcut.a.api.gips.AGipsAPI;
import gipsl.all.build.equivalence.shortcut.a.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class EquivalenceAShortcutConnector extends AConnector {

	public EquivalenceAShortcutConnector(final String modelPath) {
		api = new AGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.override(api);
	}

	@Override
	public SolverOutput run(String outputPath) {
		final SolverOutput output = solve();
		((AGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public int getY() {
		final List<N2nMapping> selectedMappingsWithY1 = ((AGipsAPI) api).getN2n().getMappings().values().stream()
				.filter(m -> m.getAdditionalVariables().get("y").getValue().intValue() == 100)
				.collect(Collectors.toList());
		assert selectedMappingsWithY1.size() == 1
				: "Number of mapping instances is not equal to 1. Value: " + selectedMappingsWithY1.size();

		return selectedMappingsWithY1.get(0).getAdditionalVariables().get("y").getValue().intValue();
	}

}

package gipsl.all.build.equivalence.shortcut.b.connector;

import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.equivalence.shortcut.b.api.gips.BGipsAPI;
import gipsl.all.build.equivalence.shortcut.b.api.gips.mapping.N2nMapping;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class EquivalenceBShortcutConnector extends AConnector {

	public EquivalenceBShortcutConnector(final String modelPath) {
		api = new BGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.override(api);
	}

	@Override
	public SolverOutput run(String outputPath) {
		final SolverOutput output = solve();
		((BGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public int getY() {
		final List<N2nMapping> selectedMappingsWithY1 = ((BGipsAPI) api).getN2n().getMappings().values().stream()
				.filter(m -> m.getAdditionalVariables().get("y").getValue().intValue() == 1)
				.collect(Collectors.toList());
		assert selectedMappingsWithY1.size() == 1
				: "Number of mapping instances is not equal to 1. Value: " + selectedMappingsWithY1.size();

		return selectedMappingsWithY1.get(0).getAdditionalVariables().get("y").getValue().intValue();
	}

}

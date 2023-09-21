package gipsl.all.build.mappingpreservation.connector;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.mappingpreservation.api.gips.MappingpreservationGipsAPI;
import gipsl.all.build.mappingpreservation.api.gips.mapping.N2nMapping;
import gipsl.all.build.mappingpreservation.api.matches.MapVnodeMatch;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MappingPreservationConnector extends AConnector {

	public MappingPreservationConnector(final String modelPath) {
		api = new MappingpreservationGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((MappingpreservationGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

	public ILPSolverOutput runWithNoApplication(final String outputPath) {
		final ILPSolverOutput output = solve();
		save(outputPath);
		return output;
	}

	public List<Optional<MapVnodeMatch>> applyMappingWithVnodeName(final String vnodeName) {
		final var mappings = ((MappingpreservationGipsAPI) api).getN2n().getNonZeroVariableMappings();
		final var filtered = mappings.stream().filter(t -> {
			return t.getMatch().getVnode().getName().equals(vnodeName);
		}).toList();

		// Check that only one mapping should be applied
		if (filtered.size() != 1) {
			throw new UnsupportedOperationException();
		}

		// Check if mapping to be applied has a value > 0
		if (!(filtered.get(0).getValue() > 0)) {
			throw new InternalError();
		}

		final var rule = ((MappingpreservationGipsAPI) api).getN2n().getGTRule();
		return filtered.stream().map(m -> rule.apply(m.getMatch(), true)).collect(Collectors.toList());
	}

	public void save(final String path) {
		super.save(path);
	}

	public Collection<N2nMapping> getMappings() {
		return ((MappingpreservationGipsAPI) api).getN2n().getMappings().values();
	}

}

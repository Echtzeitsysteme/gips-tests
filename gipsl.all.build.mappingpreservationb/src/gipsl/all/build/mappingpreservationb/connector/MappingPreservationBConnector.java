package gipsl.all.build.mappingpreservationb.connector;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

import gipsl.all.build.mappingpreservationb.api.gips.MappingpreservationbGipsAPI;
import gipsl.all.build.mappingpreservationb.api.gips.mapping.IncrIfZeroMapping;
import gipsl.all.build.mappingpreservationb.api.gips.mapping.IncrMapping;
import gipsl.all.build.mappingpreservationb.api.matches.IncrementVNodeRDMatch;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MappingPreservationBConnector extends AConnector {

	public MappingPreservationBConnector(final String modelPath) {
		api = new MappingpreservationbGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		save(outputPath);
		return output;
	}

	public ILPSolverOutput runWithNoApplication(final String outputPath) {
		final ILPSolverOutput output = solve();
		save(outputPath);
		return output;
	}

	public void applyIncrMatches() {
		((MappingpreservationbGipsAPI) api).getIncr().applyNonZeroMappings();
	}

//	public List<Optional<IncrementVNodeRDMatch>> applyMappingWithVnodeName(final String vnodeName) {
//		final var mappings = ((MappingpreservationbGipsAPI) api).getIncr().getNonZeroVariableMappings();
//		final var filtered = mappings.stream().filter(t -> {
//			return t.getMatch().getVnode().getName().equals(vnodeName);
//		}).toList();
//
//		// Check that only one mapping should be applied
//		if (filtered.size() != 1) {
//			throw new UnsupportedOperationException();
//		}
//
//		// Check if mapping to be applied has a value > 0
//		if (!(filtered.get(0).getValue() > 0)) {
//			throw new InternalError();
//		}
//
//		final var rule = ((MappingpreservationbGipsAPI) api).getIncr().getGTRule();
//		return filtered.stream().map(m -> rule.apply(m.getMatch(), true)).collect(Collectors.toList());
//	}

	public void save(final String path) {
		super.save(path);
	}

	public Collection<IncrMapping> getIncrMappings() {
		return ((MappingpreservationbGipsAPI) api).getIncr().getMappings().values();
	}

	public Collection<IncrIfZeroMapping> getIncrIfZeroMappings() {
		return ((MappingpreservationbGipsAPI) api).getIncrIfZero().getMappings().values();
	}

}

package gips.sequential.execution.connector;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.gips.core.milp.SolverOutput;

import gips.sequential.execution.api.gips.ExecutionGipsAPI;
import model.Root;
import model.VirtualContainer;
import model.VirtualResourceNode;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SequentialExecutionConnector extends AConnector {

	public SequentialExecutionConnector(final String modelPath) {
		api = new ExecutionGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((ExecutionGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	public VirtualResourceNode getVirtualNodeByName(final String name) {
		Objects.requireNonNull(name);
		final Set<VirtualResourceNode> candidates = new HashSet<VirtualResourceNode>();

		final ResourceSet rs = ((ExecutionGipsAPI) api).getEMoflonAPI().getModel();
		final Root root = (Root) rs.getResources().get(0).getContents().get(0);

		root.getContainers().forEach(container -> {
			if (container instanceof VirtualContainer virtualContainer) {
				virtualContainer.getVirtualNodes().forEach(virtualNode -> {
					if (virtualNode instanceof VirtualResourceNode virtualResourceNode) {
						if (virtualResourceNode.getName().equals(name)) {
							candidates.add(virtualResourceNode);
						}
					}
				});
			}
		});

		if (candidates.size() != 1) {
			throw new UnsupportedOperationException();
		}

		return candidates.iterator().next();
	}

}

package gips.xmiinit.connector;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.milp.SolverOutput;

import gips.xmiinit.api.gips.XmiinitGipsAPI;
import hipe.engine.config.HiPEPathOptions;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class XmiInitConnector extends AConnector {

	public XmiInitConnector(final String gipsModelPath, final String modelPath, final String ibexPatternPath,
			final String hipeNetworkPath, final String hipeEngineClassName) {
		// Remove compiled XMI files to verify the loading is working correctly

		// HiPE configuration
		HiPEPathOptions.getInstance().setNetworkPath(URI.createFileURI(hipeNetworkPath));
		HiPEPathOptions.getInstance().setEngineClassName(hipeEngineClassName);

		// Create a new GIPS API object
		api = new XmiinitGipsAPI();

		// Configuration: GIPS model XMI, model XMI, IBeX pattern XMI
		api.init(GipsTestUtils.pathToUri(gipsModelPath), GipsTestUtils.pathToUri(modelPath),
				GipsTestUtils.pathToUri(ibexPatternPath));
		GlobalTestConfig.overrideSolver(api);
	}

	/**
	 * Parameter outputPath will be ignored.
	 * 
	 * @param outputPath Will be ignored.
	 * @return ILP solver output object.
	 */
	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((XmiinitGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

	/**
	 * Resets both HiPE path options for all following tests.
	 */
	public void resetHiPEPathOptions() {
		HiPEPathOptions.getInstance().resetNetworkPath();
		HiPEPathOptions.getInstance().resetEngineClassName();
	}

}

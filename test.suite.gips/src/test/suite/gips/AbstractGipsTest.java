package test.suite.gips;

import org.junit.jupiter.api.Test;

import test.suite.gips.utils.GipsTestUtils;

public abstract class AbstractGipsTest {

	public String getProjectName() {
		String packageName = getConnectorClass().getPackageName();
		if (packageName.contains(".connector")) {
			packageName = packageName.substring(0, packageName.indexOf(".connector"));
		}
		// The project name equals the package name per default in all GIPS test
		// projects.
		System.err.println(packageName);
		return packageName;
	}

	@Test
	public void testGeneratedFiles() {
		GipsTestUtils.checkIfFileGenerated(
				GipsTestUtils.constructFilePath(getProjectName(), getProjectName(), "gips-model.xmi"));
	}

	public abstract Class<?> getConnectorClass();

}

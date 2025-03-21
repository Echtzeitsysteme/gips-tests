package test.suite.gips.launchconfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gips.launchconfig.connector.LaunchConfigConnector;
import test.suite.gips.AbstractGipsTest;

public class GipsLaunchConfigGeneratorTest extends AbstractGipsTest {

	private final String launchFilePath = "../gips.launchconfig/src-gen/gips/launchconfig/api/gips/LaunchconfigLauncher.launch";
	final String mainMethodRefName = "mySpecificMainName";
	final String licenseRefName = "zz";
	final String homeRefName = "xy";

	@Test
	public void testLaunchFileExists() {
		// Check if file exists
		final File testFile = new File(launchFilePath);
		assertTrue(testFile.exists());
		assertFalse(testFile.isDirectory());
	}

	@Test
	public void testLaunchFileMainName() {
		// Pre-requesites
		testLaunchFileExists();

		final String fileContent = readLaunchFile();
		assertTrue(fileContent.contains(mainMethodRefName));

		final String lineContent = getLineWithString(fileContent, mainMethodRefName);
		assertNotNull(lineContent);
		assertTrue(lineContent.contains("\"org.eclipse.jdt.launching.MAIN_TYPE\""));
	}

	@Test
	public void testLaunchFileHomeEnv() {
		// Pre-requesites
		testLaunchFileExists();

		final String fileContent = readLaunchFile();
		assertTrue(fileContent.contains(homeRefName));

		// GRB_LICENSE_FILE
		final String lineContentGurobiHome = getLineWithString(fileContent, "\"GUROBI_HOME\"");
		assertNotNull(lineContentGurobiHome);
		assertTrue(lineContentGurobiHome.contains("\"xy\""));

		// LD_LIBRARY_PATH
		final String lineContentLd = getLineWithString(fileContent, "\"LD_LIBRARY_PATH\"");
		assertNotNull(lineContentLd);
		assertTrue(lineContentLd.contains("\"xy/lib/\""));

		// PATH
		final String lineContentPath = getLineWithString(fileContent, "\"PATH\"");
		assertNotNull(lineContentPath);
		assertTrue(lineContentPath.contains("\"xy/bin/\""));
	}

	@Test
	public void testLaunchFileLicenseEnv() {
		// Pre-requesites
		testLaunchFileExists();

		final String fileContent = readLaunchFile();
		assertTrue(fileContent.contains(licenseRefName));

		final String lineContent = getLineWithString(fileContent, licenseRefName);
		assertNotNull(lineContent);
		assertTrue(lineContent.contains("\"GRB_LICENSE_FILE\""));
	}

	/*
	 * Utilities
	 */

	@Override
	public Class<?> getConnectorClass() {
		return LaunchConfigConnector.class;
	}

	private String readLaunchFile() {
		String content = "";
		try {
			content = Files.readString(Path.of(launchFilePath), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			Assertions.fail(e.getStackTrace().toString());
		}
		return content;
	}

	private String getLineWithString(final String all, final String toSearch) {
		final String[] lines = all.split(System.lineSeparator());

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains(toSearch)) {
				return lines[i];
			}
		}

		return null;
	}

}

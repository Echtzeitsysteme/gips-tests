package test.suite.gips.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;

public class TextFileAsserts {
	private TextFileAsserts() {
	}

	public static List<String> readTextFile(Path path) {
		try {
			List<String> allLines = Files.readAllLines(path);
			return allLines;
		} catch (IOException e) {
			Assertions.fail(e);
			return null;
		}
	}

	public static void deleteFile(Path path) {
		try {
			if (Files.exists(path)) {
				System.out.println("Deleting: " + path);
				Files.deleteIfExists(path);
			}
		} catch (IOException e) {
			Assertions.fail(e);
		}
	}

	public static void assertTextContainsNot(List<String> lines, Pattern regex) {
		for (int i = 0; i < lines.size(); ++i) {
			Matcher m = regex.matcher(lines.get(i));
			if (m.find()) {
				String msg = String.format("Found pattern '%s' in line %d, character %d as '%s'", regex, i + 1,
						m.start(), m.group());
				Assertions.fail(msg);
			}
		}
	}

	public static void assertTextContains(List<String> lines, Pattern regex) {
		for (int i = 0; i < lines.size(); ++i) {
			Matcher m = regex.matcher(lines.get(i));
			if (m.find()) {
				return;
			}
		}

		Assertions.fail(String.format("Unable to find pattern '%s'", regex));
	}

	public static void assertTextContainsExactly(List<String> lines, Pattern regex, int exactly) {
		List<Integer> counter = new LinkedList<>();
		for (int i = 0; i < lines.size(); ++i) {
			Matcher m = regex.matcher(lines.get(i));
			while (m.find()) {
				counter.add(i);
			}
		}

		if (counter.size() == 0) {
			Assertions.fail(String.format("Unable to find pattern '%s'", regex));
		} else if (counter.size() != exactly) {
			String msg = String.format("Found pattern '%s' %d times, expected to find it %d times", regex,
					counter.size(), exactly);
			Assertions.fail(msg);
		}
	}

}

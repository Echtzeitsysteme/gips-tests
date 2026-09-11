package test.suite.gipsl.unit;

import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.tests.GipslTestInjectorProvider;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

@ExtendWith(InjectionExtension.class)
@InjectWith(GipslTestInjectorProvider.class)
public abstract class AbstractParserTest {

	@Inject
	protected ParseHelper<EditorGTFile> parseHelper;

	@Inject
	protected ValidationTestHelper validationTestHelper;

}

package org.emoflon.gips.gipsl.tests;

import org.emoflon.gips.gipsl.GipslRuntimeModule;
import org.emoflon.gips.gipsl.GipslStandaloneSetup;
import org.emoflon.gips.gipsl.validation.GipslValidator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class GipslTestInjectorProvider extends GipslInjectorProvider {

	@Override
	protected Injector internalCreateInjector() {
		return new GipslStandaloneSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(createModule());
			}
		}.createInjectorAndDoEMFRegistration();
	}

	protected Module createModule() {
		GipslRuntimeModule module = createRuntimeModule();

		return Modules.override(module).with(binder -> {
			binder.bind(GipslValidator.class).to(GipslTestValidator.class);
		});
	}

}

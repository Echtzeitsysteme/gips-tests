package test.suite.gips.tsp.utils;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import citymodel.City;
import citymodel.CitymodelFactory;
import citymodel.Root;

public class TspModelGenerator {

	private static Root root = CitymodelFactory.eINSTANCE.createRoot();

	public static void genCity(final int x, final int y) {
		final City c = CitymodelFactory.eINSTANCE.createCity();
		c.setX(x);
		c.setY(y);
		root.getCities().add(c);
	}

	public static void genCities(final List<Integer[]> coords) {
		for (final Integer[] c : coords) {
			if (c.length != 2) {
				throw new IllegalArgumentException();
			}

			genCity(c[0], c[1]);
		}
	}

	public static Root getRoot() {
		return root;
	}

	public static void reset() {
		root = CitymodelFactory.eINSTANCE.createRoot();
	}

	public static Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		root = (Root) res.getContents().get(0);
		return root;
	}

	public static void persistModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		// Create new model for saving
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource r = rs.createResource(absPath);
		// Fetch model contents from eMoflon
		r.getContents().add(root);
		try {
			r.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}

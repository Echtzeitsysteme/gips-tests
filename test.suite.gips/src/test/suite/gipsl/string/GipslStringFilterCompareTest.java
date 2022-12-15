package test.suite.gipsl.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.string.compare.filter.connector.StringCompareFilterConnector;
import stringmodel.Element;
import stringmodel.Guest;
import stringmodel.Host;
import stringmodel.Root;

public class GipslStringFilterCompareTest extends AGipslStringTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new StringCompareFilterConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genHost("a");
		gen.genGuest("b");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
		checkConsistency(1);
	}

	@Test
	public void testMap2to1() {
		gen.genHost("a");
		gen.genGuest("b");
		gen.genGuest("c");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
		checkConsistency(2);
	}

	@Test
	public void testMap100to1() {
		gen.genHost("a");
		for (int i = 1; i <= 100; i++) {
			gen.genGuest(String.valueOf(i));
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(100, ret.objectiveValue());
		checkConsistency(100);
	}

	@Test
	public void testMap10to5() {
		for (int i = 1; i <= 5; i++) {
			gen.genHost("h" + i);
		}
		for (int i = 1; i <= 10; i++) {
			gen.genGuest(String.valueOf(i));
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
		checkConsistency(10);
	}

	// Utilities

	private void checkConsistency(final int refNumberMappedGuests) {
		final Root root = loadModelAfterTest();

		// Get all hosts and guests
		final Set<Host> hosts = new HashSet<>();
		final Set<Guest> guests = new HashSet<>();

		root.getElements().forEach(e -> {
			if (e instanceof Host h) {
				hosts.add(h);
			} else if (e instanceof Guest g) {
				guests.add(g);
			}
		});

		// Check number of mapped guests
		checkNumberOfMappedGuests(refNumberMappedGuests, root);

		// Forward and backward consistency check
		for (final Host h : hosts) {
			h.getGuests().forEach(g -> {
				checkGuestConsistency(g);
			});
		}

		for (final Guest g : guests) {
			checkGuestConsistency(g);
		}
	}

	private void checkGuestConsistency(final Guest g) {
		assertNotNull(g.getHost());
		assertNotNull(g.getHost().getGuests());
		assertFalse(g.getHost().getGuests().isEmpty());
		assertTrue(g.getHost().getGuests().contains(g));
	}

	private void checkNumberOfMappedGuests(final int ref, final Root root) {
		int guestCntr = 0;
		for (final Element e : root.getElements()) {
			if (e instanceof Guest g && g.getHost() != null) {
				guestCntr++;
			}
		}
		assertEquals(ref, guestCntr);
	}

}

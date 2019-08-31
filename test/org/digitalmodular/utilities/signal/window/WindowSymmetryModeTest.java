package org.digitalmodular.utilities.signal.window;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-27
public class WindowSymmetryModeTest {

	@Test
	public void DFT_EVEN_begin() throws Exception {
		assertEquals(0.0, WindowSymmetryMode.DFT_EVEN.getValueAt(0, 10));
	}

	@Test
	public void DFT_EVEN_center() throws Exception {
		assertEquals(0.5, WindowSymmetryMode.DFT_EVEN.getValueAt(5, 10));
	}

	@Test
	public void SYMMETRIC_begin() throws Exception {
		assertEquals(0.05, WindowSymmetryMode.SYMMETRIC.getValueAt(0, 10));
	}

	@Test
	public void SYMMETRIC_center() throws Exception {
		assertEquals(0.5, WindowSymmetryMode.SYMMETRIC.getValueAt(5, 11));
	}

	@Test
	public void PERIODIC_begin() throws Exception {
		assertEquals(0.05, WindowSymmetryMode.PERIODIC.getValueAt(0, 9));
	}

	@Test
	public void PERIODIC_center() throws Exception {
		assertEquals(0.5, WindowSymmetryMode.PERIODIC.getValueAt(5, 10));
	}
}

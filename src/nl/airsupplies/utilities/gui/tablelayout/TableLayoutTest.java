package nl.airsupplies.utilities.gui.tablelayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import static nl.airsupplies.utilities.gui.tablelayout.TableLayout.MINIMUM;
import static nl.airsupplies.utilities.gui.tablelayout.TableLayout.PREFERRED;
import static nl.airsupplies.utilities.gui.tablelayout.TableLayout.weightToString;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-12
final class TableLayoutTest {
	public static void main(String... args) {
		SwingUtilities.invokeLater(TableLayoutTest::new);
	}

	private TableLayoutTest() {
		Number[] widths = {40, 10, MINIMUM, MINIMUM, PREFERRED, PREFERRED, 1.0, 1.0};
//		Number[] widths  = {40.0, 10.0, 50.0, 70.0, 100.0, 120.0, 190.0, 210.0};
//		Number[] widths  = {10, 20, 30, 40, 50, 60, 70, 80};
		Number[] heights = {16, PREFERRED, 1.0, 2.0, MINIMUM, 1.5};
		JPanel   p       = new JPanel(new TableLayout(8, widths, heights));

		for (int i = 0; i < 100; i++) {
			if (i < widths.length) {
				p.add(new JButton(weightToString(widths[i])));
			} else if (i % widths.length == 0) {
				p.add(new JButton(weightToString(heights[Math.min(i / widths.length, heights.length - 1)])));
			} else if (i == 23) {
				p.add(new JButton("<html>Foo bar<br>baz quux"));
			} else if (((i % widths.length) & 1) == 0) {
				p.add(new JButton("l"));
			} else {
				p.add(new JButton("Foo bar baz quux"));
			}
		}

		p.setBorder(new TitledBorder("TitledBorder"));

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		f.setContentPane(p);

		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}

package nl.airsupplies.utilities.gui.progress;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 */
// Created 2012-05-12
public class MultiProgressDialog extends JDialog implements MultiProgressListener {
	boolean autoShow  = false;
	boolean autoClose = false;

	private final JLabel         taskNameLabel = new JLabel("<whatLabel>", SwingConstants.CENTER);
	private       JProgressBar[] progressBars;
	private final JButton        cancelButton  = new JButton("Cancel");

	public MultiProgressDialog(@Nullable Frame owner,
	                           String title,
	                           int numProgressBars) {
		super(owner, requireNonNull(title, "title"), false);
		requireAtLeast(1, numProgressBars, "numProgressBars");

		makeLayout(owner, numProgressBars);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public boolean isAutoShow() {
		return autoShow;
	}

	public void setAutoShow(boolean autoShow) {
		this.autoShow = autoShow;
	}

	public boolean isAutoClose() {
		return autoClose;
	}

	public void setAutoClose(boolean autoClose) {
		this.autoClose = autoClose;
	}

	private void makeLayout(@Nullable Component owner, int numProgressBars) {
		setLayout(new BorderLayout());

		progressBars = new JProgressBar[numProgressBars];

		{
			JPanel p = new JPanel(new GridLayout(2, 1));

			p.add(taskNameLabel);
			p.add(Box.createGlue());

			add(p, BorderLayout.NORTH);
		}
		{
			JPanel p = new JPanel(new GridLayout(numProgressBars, 1));

			for (int i = 0; i < numProgressBars; i++) {
				progressBars[i] = new JProgressBar();
				progressBars[i].setPreferredSize(new Dimension(540, 20));
				p.add(progressBars[i]);
			}

			add(p, BorderLayout.CENTER);
		}
		{
			JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));

			cancelButton.setEnabled(false);
			p.add(cancelButton);

			add(p, BorderLayout.SOUTH);
		}

		pack();
		setLocationRelativeTo(owner);

		// Needs to be set after pack() to ensure layout stability
		taskNameLabel.setText("");
	}

	public void addCancelListener(ActionListener cancelListener) {
		cancelButton.addActionListener(cancelListener);
		cancelButton.setEnabled(cancelButton.getActionListeners().length != 0);
	}

	public void removeCancelListener(ActionListener cancelListener) {
		cancelButton.removeActionListener(cancelListener);
		cancelButton.setEnabled(cancelButton.getActionListeners().length != 0);
	}

	@Override
	public int getNumProgressBars() {
		return progressBars.length;
	}

	/**
	 * Sets the text above the progress bar.
	 */
	public void setTaskName(String taskName) {
		requireStringNotEmpty(taskName, "taskName");

		if (autoShow && !isVisible()) {
			setVisible(true);
		}

		taskNameLabel.setText(taskName);
	}

	@Override
	public void progressUpdated(int progressBarIndex, ProgressEvent evt) {
		requireRange(0, progressBars.length - 1, progressBarIndex, "progressBarIndex");
		requireNonNull(evt, "evt");

		if (autoShow && !isVisible()) {
			setVisible(true);
		}

		JProgressBar progressBar = progressBars[progressBarIndex];

		boolean indeterminate = evt.getTotal() <= 0;

		if (indeterminate) {
			progressBar.setIndeterminate(true);
			progressBar.setMaximum(1);
			progressBar.setValue(1);
		} else {
			if (autoClose && evt.getProgress() >= evt.getTotal()) {
				setVisible(false);
				return;
			}

			progressBar.setIndeterminate(false);
			progressBar.setMaximum(evt.getTotal());
			progressBar.setValue(evt.getProgress());
		}

		progressBar.setString(evt.getText());
		progressBar.setStringPainted(!evt.getText().isEmpty());

		if (!System.getProperty("os.name").toLowerCase().contains("win")) {
			Toolkit.getDefaultToolkit().sync(); // Recommended, except on Windows where this is superfluous.
		}
	}
}

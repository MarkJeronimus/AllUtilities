package nl.airsupplies.utilities.broken;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.gui.progress.ProgressEvent;
import nl.airsupplies.utilities.gui.progress.ProgressListener;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireNullOrStringNotEmpty;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2008-01-07
public final class ProgressDialog extends JDialog implements ProgressListener, WindowListener {
	private boolean autoShow  = false;
	private boolean autoClose = false;

	private static final String CANCEL_TEXT = "Cancel";
	private static final String CLOSE_TEXT  = "Close";

	private final JLabel       taskNameLabel = new JLabel("<task name>", SwingConstants.CENTER);
	private final JProgressBar progressBar   = new JProgressBar();
	private final JButton      cancelButton  = new JButton(CANCEL_TEXT);

	public ProgressDialog(JComponent owner, String title) {
		super((Frame)owner.getTopLevelAncestor(), title, false);

		makeLayout(owner);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
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

	private void makeLayout(Component owner) {
		setLayout(new BorderLayout());

		{
			JPanel p = new JPanel(new GridLayout(2, 1));

			p.add(taskNameLabel);

			add(p, BorderLayout.NORTH);
		}
		{
			JPanel p = new JPanel(new GridLayout(1, 1));

			progressBar.setPreferredSize(new Dimension(540, 20));
			p.add(progressBar);

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

	/**
	 * @param total if negative the progress bar will be 'indeterminate'.
	 */
	public void setProgress(int progress, int total, String progressItemName) {
		progressBar.setIndeterminate(total < 0);

		if (total > 0) {
			progressBar.setMaximum(total);
		}

		progressBar.setValue(progress);
	}

	/**
	 * Sets the text above the progress bar.
	 */
	public void setTaskName(String taskName) {
		requireStringNotEmpty(taskName, "taskName");

		taskNameLabel.setText(taskName);
	}

	/**
	 * Places a text inside the progress bar.
	 */
	public void setProgressMessage(@Nullable String item) {
		requireNullOrStringNotEmpty(item, "item");

		progressBar.setString(item);
		progressBar.setStringPainted(item != null && !item.isEmpty());
	}

	/**
	 * @param evt if the 'total' property is negative, the progress bar will be 'indeterminate'.
	 */
	@Override
	public void progressUpdated(ProgressEvent evt) {
		requireNonNull(evt, "evt");

		if (autoShow && !isVisible()) {
			setVisible(true);
		}

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

	@Override
	public void progressCompleted(ProgressEvent evt) {
		boolean indeterminate = evt.getTotal() <= 0;
		progressBar.setIndeterminate(false);

		if (!indeterminate) {
			progressBar.setMaximum(evt.getTotal());
			progressBar.setValue(evt.getProgress());
		} else {
			progressBar.setMaximum(1);
			progressBar.setValue(evt.isComplete() ? 1 : 0);
		}

		progressBar.setString(evt.getText());
		progressBar.setStringPainted(!evt.getText().isEmpty());

		cancelButton.setText("Close");
		cancelButton.setEnabled(true);
		for (ActionListener listener : cancelButton.getActionListeners()) {
			cancelButton.removeActionListener(listener);
		}

		cancelButton.addActionListener(ignored -> dispose());
	}

	public boolean isCompeted() {
		return cancelButton.getText().equals(CLOSE_TEXT);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (cancelButton.isEnabled()) {
			cancelButton.doClick();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}

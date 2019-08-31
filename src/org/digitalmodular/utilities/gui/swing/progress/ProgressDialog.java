/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.swing.progress;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireStringNotEmpty;

/**
 * @author Mark Jeronimus
 */
// Created 2008-01-07
public class ProgressDialog extends JDialog implements ProgressListener {
	boolean autoShow  = false;
	boolean autoClose = false;

	private final JLabel       taskNameLabel = new JLabel("<whatLabel>", SwingConstants.CENTER);
	private final JProgressBar progressBar   = new JProgressBar();
	private final JButton      cancelButton  = new JButton("Cancel");

	public ProgressDialog(JComponent owner, String title) {
		super((Frame)owner.getTopLevelAncestor(), title, false);

		makeLayout(owner);
	}

	public boolean isAutoShow()                 { return autoShow; }

	public void setAutoShow(boolean autoShow)   { this.autoShow = autoShow; }

	public boolean isAutoClose()                { return autoClose; }

	public void setAutoClose(boolean autoClose) { this.autoClose = autoClose; }

	private void makeLayout(Component owner) {
		setLayout(new BorderLayout());

		{
			JPanel p = new JPanel(new GridLayout(2, 1));

			p.add(taskNameLabel);
//			p.add(Box.createGlue());

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
	 * Sets the text above the progress bar.
	 */
	public void setTaskName(String taskName) {
		requireStringNotEmpty(taskName, "taskName");

		if (autoShow && !isVisible())
			setVisible(true);

		taskNameLabel.setText(taskName);
	}

	/**
	 * @param evt if the 'total' property is negative, the progress bar will be 'indeterminate'.
	 */
	@Override
	public void progressUpdated(ProgressEvent evt) {
		requireNonNull(evt, "evt");

		if (autoShow && !isVisible())
			setVisible(true);

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

		if (!System.getProperty("os.name").toLowerCase().contains("win"))
			Toolkit.getDefaultToolkit().sync(); // Recommended, except on Windows where this is superfluous.
	}
}

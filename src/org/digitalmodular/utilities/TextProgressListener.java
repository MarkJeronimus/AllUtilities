package org.digitalmodular.utilities;

import java.io.PrintStream;

import org.digitalmodular.utilities.graphics.swing.progress.ProgressEvent;
import org.digitalmodular.utilities.graphics.swing.progress.ProgressListener;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2011-02-24
public class TextProgressListener implements ProgressListener {
	private final PrintStream writer;
	private final int         updateGranularity;

	private String lastPrinted  = "";
	private int    nextUpdateAt = 0;

	public TextProgressListener(PrintStream writer, int updateGranularity) {
		this.writer            = requireNonNull(writer, "writer");
		this.updateGranularity = requireAtLeast(1, updateGranularity, "updateGranularity");
	}

	@Override
	public void progressUpdated(ProgressEvent evt) {
		boolean indeterminate = evt.getTotal() <= 0;
		long    progress      = evt.getProgress();
		boolean complete      = !indeterminate && progress >= evt.getTotal();

		long overshotBy = progress - nextUpdateAt;
		if (overshotBy < 0 && !complete) {
			return;
		}

		nextUpdateAt += (overshotBy / updateGranularity + 1) * updateGranularity;

		eraseLine();

		lastPrinted = indeterminate
		              ? HexUtilities.toString(progress) + ' ' + evt.getText()
		              : HexUtilities.toString(progress) + '/' + HexUtilities.toString(evt.getTotal()) + ' ' +
		                evt.getText();

		writer.print(lastPrinted);

		if (complete) {
			writer.println();
			lastPrinted  = "";
			nextUpdateAt = 0;
		}
	}

	private void eraseLine() {
		lastPrinted = StringUtilities.repeatChar('\b', lastPrinted.length());
		writer.print(lastPrinted);
		lastPrinted = "";
	}
}

package nl.airsupplies.utilities;

import java.io.PrintStream;

import nl.airsupplies.utilities.gui.progress.ProgressEvent;
import nl.airsupplies.utilities.gui.progress.ProgressListener;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

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

		long overshotBy = progress - nextUpdateAt;
		if (overshotBy < 0) {
			return;
		}

		nextUpdateAt += (overshotBy / updateGranularity + 1) * updateGranularity;

		eraseLine();

		lastPrinted = indeterminate
		              ? progress + " " + evt.getText()
		              : progress + "/" + evt.getTotal() + ' ' + evt.getText();

		writer.print(lastPrinted);
	}

	@Override
	public void progressCompleted(ProgressEvent evt) {
		boolean indeterminate = evt.getTotal() <= 0;
		long    progress      = evt.getProgress();

		eraseLine();

		lastPrinted = indeterminate
		              ? progress + " " + evt.getText()
		              : progress + "/" + evt.getTotal() + ' ' + evt.getText();

		writer.print(lastPrinted);

		writer.println();
		lastPrinted  = "";
		nextUpdateAt = 0;
	}

	private void eraseLine() {
		lastPrinted = StringUtilities.repeatChar('\b', lastPrinted.length());
		writer.print(lastPrinted);
		lastPrinted = "";
	}
}

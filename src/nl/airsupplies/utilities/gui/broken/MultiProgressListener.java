package nl.airsupplies.utilities.gui.broken;

import nl.airsupplies.utilities.gui.progress.ProgressEvent;

/**
 * @author Mark Jeronimus
 */
// Created 2019-08-08 Copied from ProgressListener
public interface MultiProgressListener {
	int getNumProgressBars();

	void progressUpdated(int progressBarIndex, ProgressEvent evt);

//	default MultiProgressListener makeListenerForProgressBar(int index) {
//		return evt -> progressUpdated(index, evt);
//	}
}

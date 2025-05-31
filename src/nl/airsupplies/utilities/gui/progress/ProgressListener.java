package nl.airsupplies.utilities.gui.progress;

import java.util.EventListener;

/**
 * @author Mark Jeronimus
 */
public interface ProgressListener extends EventListener {
	void progressUpdated(ProgressEvent e);
}

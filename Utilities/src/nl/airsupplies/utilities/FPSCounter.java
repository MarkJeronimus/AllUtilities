package nl.airsupplies.utilities;

import java.util.Arrays;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-13
public class FPSCounter {
	private final long[] lastTime = new long[64];
	private       int    pointer;
	private       int    count;

	private double elapsedTime;
	private double fps = 0;

	public void start() {
		long currentTime = System.nanoTime();
		pointer = 0;
		count   = 0;
		Arrays.fill(lastTime, currentTime);
	}

	public void update() {
		long currentTime = System.nanoTime();

		int lastPointer = pointer;
		pointer = pointer + 1 & 63;

		if (count < 64) {
			count++;
		}

		fps         = 1.0e9 * count / (currentTime - lastTime[pointer]);
		elapsedTime = (currentTime - lastTime[lastPointer]) / 1.0e9;

		lastTime[pointer] = currentTime;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}

	public double getFPS() {
		return fps;
	}
}

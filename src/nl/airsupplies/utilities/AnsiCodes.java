package nl.airsupplies.utilities;

import java.awt.Color;

import nl.airsupplies.utilities.annotation.StaticClass;

/**
 * @author Mark Jeronimus
 */
// Created 2021-06-19
@StaticClass
public final class AnsiCodes {
	public static String resetAll() {
		return "\u001B[0m";
	}

	public static String setBold(boolean bold) {
		return bold ? "\u001B[1m" : "\u001B[2m";
	}

	public static String setItalic(boolean italic) {
		return italic ? "\u001B[3m" : "\u001B[23m";
	}

	public static String setUnderline(boolean underline) {
		return underline ? "\u001B[4m" : "\u001B[24m";
	}

	public static String setReverseVideo(boolean reverseVideo) {
		return reverseVideo ? "\u001B[7m" : "\u001B[27m";
	}

	public static String setStrikethrough(boolean strikethrough) {
		return strikethrough ? "\u001B[9m" : "\u001B[29m";
	}

	public static String setFramed(boolean framed) {
		return framed ? "\u001B[51m" : "\u001B[54m";
	}

	public static String setBlack() {
		return "\u001B[30m";
	}

	public static String setRed() {
		return "\u001B[31m";
	}

	public static String setGreen() {
		return "\u001B[32m";
	}

	public static String setYellow() {
		return "\u001B[33m";
	}

	public static String setBlue() {
		return "\u001B[34m";
	}

	public static String setMagenta() {
		return "\u001B[35m";
	}

	public static String setCyan() {
		return "\u001B[36m";
	}

	public static String setWhite() {
		return "\u001B[37m";
	}

	public static String setColor(Color color) {
		return setColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static String setColor(int r, int g, int b) {
		return "\u001B[38;2;" +
		       NumberUtilities.clamp(r, 0, 255) + ';' +
		       NumberUtilities.clamp(g, 0, 255) + ';' +
		       NumberUtilities.clamp(b, 0, 255) + 'm';
	}

	public static String resetColor() {
		return "\u001B[39m";
	}

	public static String resetBackgroundColor() {
		return "\u001B[49m";
	}

	public static String setBackgroundBlack() {
		return "\u001B[40m";
	}

	public static String setBackgroundRed() {
		return "\u001B[41m";
	}

	public static String setBackgroundGreen() {
		return "\u001B[42m";
	}

	public static String setBackgroundYellow() {
		return "\u001B[43m";
	}

	public static String setBackgroundBlue() {
		return "\u001B[44m";
	}

	public static String setBackgroundMagenta() {
		return "\u001B[45m";
	}

	public static String setBackgroundCyan() {
		return "\u001B[46m";
	}

	public static String setBackgroundWhite() {
		return "\u001B[47m";
	}

	public static String setBackgroundColor(Color color) {
		return setBackgroundColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static String setBackgroundColor(int r, int g, int b) {
		return "\u001B[48;2;" +
		       NumberUtilities.clamp(r, 0, 255) + ';' +
		       NumberUtilities.clamp(g, 0, 255) + ';' +
		       NumberUtilities.clamp(b, 0, 255) + 'm';
	}
}

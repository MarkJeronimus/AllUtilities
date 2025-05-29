package nl.airsupplies.utilities;

import java.awt.Color;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.StaticClass;

/**
 * @author Mark Jeronimus
 */
// Created 2021-06-19
@SuppressWarnings({"ConstantConditions", "OverloadedMethodsWithSameNumberOfParameters", "ReturnOfNull",
                   "UseOfSystemOutOrSystemErr"})
@StaticClass
public final class AnsiConsole {
	public static void main(String... args) {
		println("\u001B[60m60");
		println("\u001B[61m61");
		println("\u001B[62m62");
		println("\u001B[63m63");
		println("\u001B[64m64");
		println("\u001B[65m65");
		println("\u001B[73m73");
		println("\u001B[74m74");
		println("\u001B[75m75");
	}

	public static AnsiConsole resetAll() {
		System.out.print("\u001B[0m");
		return null;
	}

	public static AnsiConsole setBold(boolean bold) {
		System.out.print(bold ? "\u001B[1m" : "\u001B[2m");
		return null;
	}

	public static AnsiConsole setItalic(boolean italic) {
		System.out.print(italic ? "\u001B[3m" : "\u001B[23m");
		return null;
	}

	public static AnsiConsole setUnderline(boolean underline) {
		System.out.print(underline ? "\u001B[4m" : "\u001B[24m");
		return null;
	}

	public static AnsiConsole setReverseVideo(boolean reverseVideo) {
		System.out.print(reverseVideo ? "\u001B[7m" : "\u001B[27m");
		return null;
	}

	public static AnsiConsole setStrikethrough(boolean strikethrough) {
		System.out.print(strikethrough ? "\u001B[9m" : "\u001B[29m");
		return null;
	}

	public static AnsiConsole setFramed(boolean framed) {
		System.out.print(framed ? "\u001B[51m" : "\u001B[54m");
		return null;
	}

	public static AnsiConsole setBlack() {
		System.out.print("\u001B[30m");
		return null;
	}

	public static AnsiConsole setRed() {
		System.out.print("\u001B[31m");
		return null;
	}

	public static AnsiConsole setGreen() {
		System.out.print("\u001B[32m");
		return null;
	}

	public static AnsiConsole setYellow() {
		System.out.print("\u001B[33m");
		return null;
	}

	public static AnsiConsole setBlue() {
		System.out.print("\u001B[34m");
		return null;
	}

	public static AnsiConsole setMagenta() {
		System.out.print("\u001B[35m");
		return null;
	}

	public static AnsiConsole setCyan() {
		System.out.print("\u001B[36m");
		return null;
	}

	public static AnsiConsole setWhite() {
		System.out.print("\u001B[37m");
		return null;
	}

	public static AnsiConsole setColor(Color color) {
		return setColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static AnsiConsole setColor(int r, int g, int b) {
		//noinspection StringConcatenationMissingWhitespace
		System.out.print("\u001B[38;2;" +
		                 NumberUtilities.clamp(r, 0, 255) + ';' +
		                 NumberUtilities.clamp(g, 0, 255) + ';' +
		                 NumberUtilities.clamp(b, 0, 255) + 'm');
		return null;
	}

	public static AnsiConsole resetColor() {
		System.out.print("\u001B[39m");
		return null;
	}

	public static AnsiConsole resetBackgroundColor() {
		System.out.print("\u001B[49m");
		return null;
	}

	public static AnsiConsole setBackgroundBlack() {
		System.out.print("\u001B[40m");
		return null;
	}

	public static AnsiConsole setBackgroundRed() {
		System.out.print("\u001B[41m");
		return null;
	}

	public static AnsiConsole setBackgroundGreen() {
		System.out.print("\u001B[42m");
		return null;
	}

	public static AnsiConsole setBackgroundYellow() {
		System.out.print("\u001B[43m");
		return null;
	}

	public static AnsiConsole setBackgroundBlue() {
		System.out.print("\u001B[44m");
		return null;
	}

	public static AnsiConsole setBackgroundMagenta() {
		System.out.print("\u001B[45m");
		return null;
	}

	public static AnsiConsole setBackgroundCyan() {
		System.out.print("\u001B[46m");
		return null;
	}

	public static AnsiConsole setBackgroundWhite() {
		System.out.print("\u001B[47m");
		return null;
	}

	public static AnsiConsole setBackgroundColor(Color color) {
		return setBackgroundColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static AnsiConsole setBackgroundColor(int r, int g, int b) {
		//noinspection StringConcatenationMissingWhitespace
		System.out.print("\u001B[48;2;" +
		                 NumberUtilities.clamp(r, 0, 255) + ';' +
		                 NumberUtilities.clamp(g, 0, 255) + ';' +
		                 NumberUtilities.clamp(b, 0, 255) + 'm');
		return null;
	}

	public static AnsiConsole flush() {
		System.out.flush();
		return null;
	}

	public static AnsiConsole print(boolean b) {
		System.out.print(b);
		return null;
	}

	public static AnsiConsole print(char c) {
		System.out.print(c);
		return null;
	}

	public static AnsiConsole print(int i) {
		System.out.print(i);
		return null;
	}

	public static AnsiConsole print(long l) {
		System.out.print(l);
		return null;
	}

	public static AnsiConsole print(float f) {
		System.out.print(f);
		return null;
	}

	public static AnsiConsole print(double d) {
		System.out.print(d);
		return null;
	}

	public static AnsiConsole print(char[] s) {
		System.out.print(s);
		return null;
	}

	public static AnsiConsole print(@Nullable String s) {
		System.out.print(s);
		return null;
	}

	public static AnsiConsole print(@Nullable Object obj) {
		System.out.print(obj);
		return null;
	}

	public static AnsiConsole println() {
		System.out.println();
		return null;
	}

	public static AnsiConsole println(boolean x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(char x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(int x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(long x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(float x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(double x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(char[] x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(@Nullable String x) {
		System.out.println(x);
		return null;
	}

	public static AnsiConsole println(@Nullable Object x) {
		System.out.println(x);
		return null;
	}
}

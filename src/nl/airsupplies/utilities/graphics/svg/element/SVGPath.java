/*
 * This file is part of PAO.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package nl.airsupplies.utilities.graphics.svg.element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.airsupplies.utilities.graphics.svg.core.SVGElement;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
public final class SVGPath extends SVGElement {
	private final List<SVGPathCommand> moves;

	public SVGPath(int initialCapacity) {
		super("path");
		initialCapacity = Math.max(initialCapacity, 2);

		moves = new ArrayList<>(initialCapacity);
	}

	public void clear() {
		moves.clear();
	}

	public void moveTo(float x, float y) {
		// Consecutive moves make no sense
		if (!moves.isEmpty() && moves.get(moves.size() - 1) instanceof SVGPathMoveTo) {
			moves.remove(moves.size() - 1);
		}

		moves.add(new SVGPathMoveTo(x, y));
	}

	public void lineTo(float x, float y) {
		moves.add(new SVGPathLineTo(x, y));
	}

	public void cubicTo(float c1x, float c1y, float c2x, float x2y, float x, float y) {
		moves.add(new SVGPathCubicTo(c1x, c1y, c2x, x2y, x, y));
	}

	public void closePath() {
		// Starting with a close command and consecutive close commands make no sense
		if (moves.isEmpty() || moves.get(moves.size() - 1) instanceof SVGPathClosePath) {
			return;
		}

		moves.add(SVGPathClosePath.INSTANCE);
	}

	public int size() {
		return moves.size();
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		out.append(" d=\"");

		EncoderState state = new EncoderState();

		for (SVGPathCommand move : moves) {
			move.encodePathCommand(out, state);
		}

		out.append("\"");
	}

	private static boolean isDigit(char c) {
		//noinspection CharacterComparison
		return c >= '0' && c <= '9';
	}

	private static final class EncoderState {
		float                           firstX          = 0.0f;
		float                           firstY          = 0.0f;
		float                           x               = 0.0f;
		float                           y               = 0.0f;
		Class<? extends SVGPathCommand> lastCommandType = SVGPathClosePath.class;
		char                            lastCommand     = 'z';
		boolean                         endsDigit       = false;

		@Override
		public String toString() {
			return "firstX=" + firstX +
			       ", firstY=" + firstY +
			       ", x=" + x +
			       ", y=" + y;
		}
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-01-23
	private abstract static class SVGPathCommand {
		public abstract void encodePathCommand(Appendable out, EncoderState state) throws IOException;

		protected static void encodeCommandChange(Appendable out,
		                                          EncoderState state,
		                                          char command,
		                                          Class<? extends SVGPathCommand> commandType) throws IOException {
			if (state.lastCommand != command) {
				out.append(command);
				state.lastCommand = command;
				state.endsDigit   = false;
			}

			if (state.lastCommandType != commandType) {
				state.firstX          = state.x;
				state.firstY          = state.y;
				state.lastCommandType = commandType;
			}
		}

		protected static void encodeCoord(Appendable out, EncoderState state, float coord) throws IOException {
//			String xString = Float.toString(coord);
			String xString = String.format("%.2f", coord);

			if (state.endsDigit) {
				out.append(' ');
			}

			out.append(xString);
			state.endsDigit = isDigit(xString.charAt(xString.length() - 1));
		}
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-01-23
	private static final class SVGPathMoveTo extends SVGPathCommand {
		private final float x;
		private final float y;

		private SVGPathMoveTo(float x, float y) {
			this.x = requireNotDegenerate(x, "x");
			this.y = requireNotDegenerate(y, "y");
		}

		@Override
		public void encodePathCommand(Appendable out, EncoderState state) throws IOException {
			encodeCommandChange(out, state, 'M', SVGPathMoveTo.class);
			encodeCoord(out, state, x);
			encodeCoord(out, state, y);

//			float dx = x - state.x;
//			float dy = y - state.y;
//			encodeCommandChange(out, state, 'm', SVGPathMoveTo.class);
//			encodeCoord(out, state, dx);
//			encodeCoord(out, state, dy);

			state.firstX = x;
			state.firstY = y;
			state.x      = x;
			state.y      = y;

			state.lastCommandType = SVGPathLineTo.class; // Automatic switch
		}
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-01-23
	private static final class SVGPathLineTo extends SVGPathCommand {
		private final float x;
		private final float y;

		private SVGPathLineTo(float x, float y) {
			this.x = requireNotDegenerate(x, "x");
			this.y = requireNotDegenerate(y, "y");
		}

		@Override
		public void encodePathCommand(Appendable out, EncoderState state) throws IOException {
			if (y == state.y) {
				if (x == state.x) {
					return;
				}

				encodeCommandChange(out, state, 'H', SVGPathMoveTo.class);
				encodeCoord(out, state, x);
			} else if (x == state.x) {
				encodeCommandChange(out, state, 'V', SVGPathMoveTo.class);
				encodeCoord(out, state, y);
			} else {
				encodeCommandChange(out, state, 'L', SVGPathMoveTo.class);
				encodeCoord(out, state, x);
				encodeCoord(out, state, y);
			}

//			float deltaX = x - state.x;
//			float deltaY = y - state.y;
//			if (deltaY == 0) {
//				if (deltaX == 0) {
//					return;
//				}
//
//				encodeCommandChange(out, state, 'h', SVGPathMoveTo.class);
//				encodeCoord(out, state, deltaX);
//			} else if (deltaX == 0) {
//				encodeCommandChange(out, state, 'v', SVGPathMoveTo.class);
//				encodeCoord(out, state, deltaY);
//			} else {
//				encodeCommandChange(out, state, 'l', SVGPathMoveTo.class);
//				encodeCoord(out, state, deltaX);
//				encodeCoord(out, state, deltaY);
//			}

			state.x = x;
			state.y = y;
		}
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-01-23
	private static final class SVGPathCubicTo extends SVGPathCommand {
		private final float c1x;
		private final float c1y;
		private final float c2x;
		private final float x2y;
		private final float x;
		private final float y;

		private SVGPathCubicTo(float c1x, float c1y, float c2x, float x2y, float x, float y) {
			this.c1x = requireNotDegenerate(c1x, "c1x");
			this.c1y = requireNotDegenerate(c1y, "c1y");
			this.c2x = requireNotDegenerate(c2x, "c2x");
			this.x2y = requireNotDegenerate(x2y, "x2y");
			this.x   = requireNotDegenerate(x, "x");
			this.y   = requireNotDegenerate(y, "y");
		}

		@Override
		public void encodePathCommand(Appendable out, EncoderState state) throws IOException {
			encodeCommandChange(out, state, 'C', SVGPathMoveTo.class);
			encodeCoord(out, state, c1x);
			encodeCoord(out, state, c1y);
			encodeCoord(out, state, c2x);
			encodeCoord(out, state, x2y);
			encodeCoord(out, state, x);
			encodeCoord(out, state, y);

			state.x = x;
			state.y = y;
		}
	}

//	/**
//	 * @author Mark Jeronimus
//	 */
//	// Created 2023-01-23
//	private static final class SVGPathQuadraticTo extends SVGPathCommand {
//		private final float x;
//		private final float y;
//
//		private SVGPathQuadraticTo(float x, float y) {
//			this.x = requireNotDegenerate(x, "x");
//			this.y = requireNotDegenerate(y, "y");
//		}
//
//		@Override
//		public void encodePathCommand(Appendable out, EncoderState state) throws IOException {
//		}
//	}
//
//	/**
//	 * @author Mark Jeronimus
//	 */
//	// Created 2023-01-23
//	private static final class SVGPathArcTo extends SVGPathCommand {
//		private final float x;
//		private final float y;
//
//		private SVGPathArcTo(float x, float y) {
//			this.x = requireNotDegenerate(x, "x");
//			this.y = requireNotDegenerate(y, "y");
//		}
//
//		@Override
//		public void encodePathCommand(Appendable out, EncoderState state) throws IOException {
//		}
//	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-01-23
	private static final class SVGPathClosePath extends SVGPathCommand {
		private static final SVGPathClosePath INSTANCE = new SVGPathClosePath();

		@Override
		public void encodePathCommand(Appendable out, EncoderState state) throws IOException {
			state.x = state.firstX;
			state.y = state.firstY;
			encodeCommandChange(out, state, 'z', SVGPathClosePath.class);
		}
	}

	@Override
	public String toString() {
		return "SVGPath{size=" + size() + '}';
	}
}

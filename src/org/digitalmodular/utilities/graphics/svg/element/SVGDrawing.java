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

package org.digitalmodular.utilities.graphics.svg.element;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.digitalmodular.utilities.graphics.svg.core.SVGContainer;
import org.digitalmodular.utilities.graphics.svg.core.SVGDef;
import org.digitalmodular.utilities.graphics.svg.core.SVGElement;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
@SuppressWarnings("SpellCheckingInspection")
public final class SVGDrawing extends SVGContainer {
	private boolean prettyPrinting;

	private Rectangle2D dimension = new Rectangle(0, 0, 400, 240);

	private final List<SVGDef> defs = new ArrayList<>(16);

	public SVGDrawing(int initialCapacity) {
		super("svg", initialCapacity);
	}

	public void setPrettyPrinting(boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
	}

	public Rectangle2D getDimension() {
		return dimension;
	}

	public void setDimension(Rectangle2D size) {
		dimension = requireNonNull(size, "dimension");
	}

	public int numDefs() {
		return defs.size();
	}

	public boolean addDef(SVGDef def) {
		return defs.add(requireNonNull(def, "def"));
	}

	public boolean addDefs(Collection<? extends SVGDef> defs) {
		return this.defs.addAll(requireNonNull(defs, "defs"));
	}

	public void clearDefs() {
		defs.clear();
	}

	public SVGDef getDef(int index) {
		return defs.get(index);
	}

	public SVGDef setDef(int index, SVGDef def) {
		return defs.set(index, requireNonNull(def, "def"));
	}

	public void addDef(int index, SVGDef def) {
		defs.add(index, requireNonNull(def, "def"));
	}

	public SVGDef removeDef(int index) {
		return defs.remove(index);
	}

	public void encode(Appendable out) throws IOException {
		encode(out, prettyPrinting ? 0 : Integer.MIN_VALUE);
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		out.append(" xmlns=\"http://www.w3.org/2000/svg\"");
//		out.append(" xmlns:svg=\"http://www.w3.org/2000/svg\"");
		out.append(" xmlns:xlink=\"http://www.w3.org/1999/xlink\"");
		out.append(" xmlns:inkscape=\"http://www.inkscape.org/namespaces/inkscape\"");
		out.append(" width=\"").append(String.valueOf(dimension.getWidth())).append("\"");
		out.append(" height=\"").append(String.valueOf(dimension.getHeight())).append("\"");
		out.append(" viewBox=\"")
		   .append(String.valueOf(dimension.getMinX())).append(' ')
		   .append(String.valueOf(dimension.getMinY())).append(' ')
		   .append(String.valueOf(dimension.getWidth())).append(' ')
		   .append(String.valueOf(dimension.getHeight())).append("\"");
	}

	@Override
	protected void encodeBody(Appendable out, int indentation) throws IOException {
		if (!defs.isEmpty()) {
			indent(out, indentation);
			out.append("<defs>");
			if (indentation >= 0) {
				out.append('\n');
			}

			for (SVGDef def : defs) {
				((SVGElement)def).encode(out, indentation + 1);
			}

			indent(out, indentation);
			out.append("</defs>");
			if (indentation >= 0) {
				out.append('\n');
			}
		}

		for (SVGElement element : this) {
			element.encode(out, indentation);
		}
	}
}

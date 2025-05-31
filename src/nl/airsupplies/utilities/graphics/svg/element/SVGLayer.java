package nl.airsupplies.utilities.graphics.svg.element;

import java.io.IOException;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-26
public final class SVGLayer extends SVGGroup {
	private String name;

	public SVGLayer(String name, int initialCapacity) {
		super(initialCapacity);

		this.name = requireNonNull(name, "name");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = requireNonNull(name, "name");
	}

	@Override
	@SuppressWarnings("SpellCheckingInspection")
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "inkscape:groupmode", "layer");
		encodeAttribute(out, "inkscape:label", name);
	}
}

package nl.airsupplies.utilities.math.rangeconverter;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public interface RangeConverter {
	double toDomain(double value);

	double fromDomain(double domain);
//    logMin = 1 / (min + 1);
//    logMax = 1 / (max + 1);
//    atanMin = Math.atan(min);
//    atanMax = Math.atan(max);
//    public enum DoubleRangeMode {
//        /**
//         * Meant to be used as an exponent value. Value swings between 0 and &infin; for x between 0 and 1. Value
// can be
//         * limited depending on min and max (full range at min=0, max=&infin;).
//         * <p>
//         * <pre>
//         * value = 1 / (x + 1) - 1
//         * </pre>
//         */
//        RECIPROCAL,
//        /**
//         * Meant to be used where arbitrary positive and negative values are used, usually multipliers. Value
// swings between
//         * -&infin; and &infin; for x between 0 and 1. Value can be limited depending on min sand max (full range at
//         * min=-&infin;, max=&infin;).
//         * <p>
//         * <pre>
//         * value = tan((x - 0.5) * pi)
//         * </pre>
//         */
//        SIGMOID_ATAN;
//    }
//    public double positionToValue(double position) {
//        switch (rangeMode) {
//            case RECIPROCAL:
//                return 1 / (logMin + (logMax - logMin) * position) - 1;
//            case SIGMOID_ATAN:
//                return Math.tan(atanMin + (atanMax - atanMin) * position);
//            default:
//                throw new UnsupportedOperationException(rangeMode + " not supported yet.");
//        }
//    }
//
//    public double valueToPosition(double value) {
//        switch (rangeMode) {
//            case RECIPROCAL:
//                return ((value + 1) * logMin - 1) / ((value + 1) * (logMin - logMax));
//            case SIGMOID_ATAN:
//                return (Math.atan(value) - atanMin) / (atanMax - atanMin);
//            default:
//                throw new UnsupportedOperationException(rangeMode + " not supported yet.");
//        }
//    }
}

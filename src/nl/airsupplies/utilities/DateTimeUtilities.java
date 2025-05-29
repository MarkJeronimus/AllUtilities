package nl.airsupplies.utilities;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2021-07-24
@UtilityClass
public final class DateTimeUtilities {
	/**
	 * @param unit One of [{@link ChronoUnit#NANOS NANOS},
	 *             {@link ChronoUnit#MILLIS MILLIS},
	 *             {@link ChronoUnit#SECONDS SECONDS},
	 *             {@link ChronoUnit#MINUTES MINUTES},
	 *             {@link ChronoUnit#HOURS HOURS},
	 *             {@link ChronoUnit#DAYS DAYS},
	 *             {@link ChronoUnit#MONTHS MONTHS},
	 *             {@link ChronoUnit#YEARS YEARS}]
	 */
	public static TemporalAdjuster quantize(int multiple, ChronoUnit unit) {
		switch (unit) {
			case NANOS:
				return quantizeNano(multiple);
			case MILLIS:
				return quantizeNano(multiple * 1000000);
			case SECONDS:
				return quantizeSecond(multiple);
			case MINUTES:
				return quantizeMinute(multiple);
			case HOURS:
				return quantizeHour(multiple);
			case DAYS:
				return quantizeDay(multiple);
			case MONTHS:
				return quantizeMonth(multiple);
			case YEARS:
				return quantizeYear(multiple);
			default:
				throw new UnsupportedOperationException(unit.toString());
		}
	}

	/**
	 * @param unit One of [{@link ChronoUnit#NANOS NANOS},
	 *             {@link ChronoUnit#MILLIS MILLIS},
	 *             {@link ChronoUnit#SECONDS SECONDS},
	 *             {@link ChronoUnit#MINUTES MINUTES},
	 *             {@link ChronoUnit#HOURS HOURS},
	 *             {@link ChronoUnit#DAYS DAYS},
	 *             {@link ChronoUnit#MONTHS MONTHS},
	 *             {@link ChronoUnit#YEARS YEARS}]
	 */
	public static TemporalAdjuster nextAligned(int multiple, ChronoUnit unit) {
		switch (unit) {
			case NANOS:
				return temporal -> temporal.with(quantizeNano(multiple)).plus(multiple, ChronoUnit.NANOS);
			case MILLIS:
				return temporal -> temporal.with(quantizeNano(multiple * 1000000)).plus(multiple, ChronoUnit.MILLIS);
			case SECONDS:
				return temporal -> temporal.with(quantizeSecond(multiple)).plus(multiple, ChronoUnit.SECONDS);
			case MINUTES:
				return temporal -> temporal.with(quantizeMinute(multiple)).plus(multiple, ChronoUnit.MINUTES);
			case HOURS:
				return temporal -> temporal.with(quantizeHour(multiple)).plus(multiple, ChronoUnit.HOURS);
			case DAYS:
				return temporal -> temporal.with(quantizeDay(multiple)).plus(multiple, ChronoUnit.DAYS);
			case MONTHS:
				return temporal -> temporal.with(quantizeMonth(multiple)).plus(multiple, ChronoUnit.MONTHS);
			case YEARS:
				return temporal -> temporal.with(quantizeYear(multiple)).plus(multiple, ChronoUnit.YEARS);
			default:
				throw new UnsupportedOperationException(unit.toString());
		}
	}

	public static TemporalAdjuster quantizeYear(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long year        = temporal.get(ChronoField.YEAR);
			long flooredYear = Math.floorDiv(year, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_DAY, 0)
			               .with(ChronoField.DAY_OF_MONTH, 0)
			               .with(ChronoField.MONTH_OF_YEAR, 0)
			               .with(ChronoField.YEAR, flooredYear);
		};
	}

	public static TemporalAdjuster quantizeMonth(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long year              = temporal.get(ChronoField.YEAR);
			long monthOfYear       = temporal.get(ChronoField.MONTH_OF_YEAR);
			long epochMonth        = year * 12 + monthOfYear;
			long flooredEpochMonth = Math.floorDiv(epochMonth, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_DAY, 0)
			               .with(ChronoField.DAY_OF_MONTH, 0)
			               .with(ChronoField.MONTH_OF_YEAR, Math.floorMod(flooredEpochMonth, 12))
			               .with(ChronoField.YEAR, Math.floorDiv(flooredEpochMonth, 12));
		};
	}

	public static TemporalAdjuster quantizeDay(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long epochDay        = temporal.getLong(ChronoField.EPOCH_DAY);
			long flooredEpochDay = Math.floorDiv(epochDay, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_DAY, 0)
			               .with(ChronoField.EPOCH_DAY, flooredEpochDay);
		};
	}

	public static TemporalAdjuster quantizeHour(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long epochDay         = temporal.getLong(ChronoField.EPOCH_DAY);
			long hour             = temporal.get(ChronoField.HOUR_OF_DAY);
			long epochHour        = epochDay * 24 + hour;
			long flooredEpochHour = Math.floorDiv(epochHour, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_SECOND, 0)
			               .with(ChronoField.SECOND_OF_MINUTE, 0)
			               .with(ChronoField.MINUTE_OF_HOUR, 0)
			               .with(ChronoField.HOUR_OF_DAY, Math.floorMod(flooredEpochHour, 24))
			               .with(ChronoField.EPOCH_DAY, Math.floorDiv(flooredEpochHour, 24));
		};
	}

	public static TemporalAdjuster quantizeMinute(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long epochDay           = temporal.getLong(ChronoField.EPOCH_DAY);
			long minuteOfDay        = temporal.get(ChronoField.MINUTE_OF_DAY);
			long epochMinute        = epochDay * 1440 + minuteOfDay;
			long flooredEpochMinute = Math.floorDiv(epochMinute, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_SECOND, 0)
			               .with(ChronoField.SECOND_OF_MINUTE, 0)
			               .with(ChronoField.MINUTE_OF_DAY, Math.floorMod(flooredEpochMinute, 1440))
			               .with(ChronoField.EPOCH_DAY, Math.floorDiv(flooredEpochMinute, 1440));
		};
	}

	public static TemporalAdjuster quantizeSecond(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long epochDay           = temporal.getLong(ChronoField.EPOCH_DAY);
			long secondOfDay        = temporal.get(ChronoField.SECOND_OF_DAY);
			long epochSecond        = epochDay * 86400 + secondOfDay;
			long flooredEpochSecond = Math.floorDiv(epochSecond, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_SECOND, 0)
			               .with(ChronoField.SECOND_OF_DAY, Math.floorMod(flooredEpochSecond, 86400))
			               .with(ChronoField.EPOCH_DAY, Math.floorDiv(flooredEpochSecond, 86400));
		};
	}

	public static TemporalAdjuster quantizeNano(int multiple) {
		requireAtLeast(1, multiple, "multiple");

		return temporal -> {
			long epochDay         = temporal.getLong(ChronoField.EPOCH_DAY);
			long nanoOfDay        = temporal.getLong(ChronoField.NANO_OF_DAY);
			long epochNano        = epochDay * 86_400_000_000_000L + nanoOfDay;
			long flooredEpochNano = Math.floorDiv(epochNano, multiple) * multiple;

			return temporal.with(ChronoField.NANO_OF_DAY, Math.floorMod(flooredEpochNano, 86_400_000_000_000L))
			               .with(ChronoField.EPOCH_DAY, Math.floorDiv(flooredEpochNano, 86_400_000_000_000L));
		};
	}
}

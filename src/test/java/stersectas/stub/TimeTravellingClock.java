package stersectas.stub;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeTravellingClock extends Clock {

	private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("UTC");
	private static final Instant DEFAULT_INSTANT = toInstant("2015-09-10T16:21:07", DEFAULT_ZONE_ID);

	private static Instant toInstant(String localDateTime, ZoneId zone) {
		return toInstant(LocalDateTime.parse(localDateTime), zone);
	}

	private static Instant toInstant(LocalDateTime localDateTime, ZoneId zone) {
		return localDateTime.atZone(zone).toInstant();
	}

	private final ZoneId zone;
	private Instant instant;

	public TimeTravellingClock() {
		zone = DEFAULT_ZONE_ID;
		instant = DEFAULT_INSTANT;
	}

	private TimeTravellingClock(Instant instant, ZoneId zone) {
		this.instant = instant;
		this.zone = zone;
	}

	public void travelThroughTimeTo(Instant instant) {
		this.instant = instant;
	}

	public void travelThroughTimeTo(LocalDateTime localDateTime) {
		travelThroughTimeTo(toInstant(localDateTime, zone));
	}

	public void travelThroughTimeTo(LocalDate localDate) {
		travelThroughTimeTo(localDate.atStartOfDay());
	}

	public void travelThroughTimeToLocalDateTime(String localDateTime) {
		travelThroughTimeTo(toInstant(localDateTime, zone));
	}

	public void travelThroughTimeToOrigin() {
		instant = DEFAULT_INSTANT;
	}

	@Override
	public ZoneId getZone() {
		return zone;
	}

	@Override
	public Clock withZone(ZoneId zone) {
		return new TimeTravellingClock(instant, zone);
	}

	@Override
	public Instant instant() {
		return instant;
	}

}
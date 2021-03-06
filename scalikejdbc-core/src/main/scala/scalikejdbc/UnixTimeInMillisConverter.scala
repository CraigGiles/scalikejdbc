package scalikejdbc

import java.util.Calendar
import org.joda.time._

/**
 * Unix Time Converter to several types.
 *
 * @param millis the milliseconds from 1970-01-01T00:00:00Z
 */
class UnixTimeInMillisConverter(val millis: Long) extends AnyVal {

  def toJavaUtilDate: java.util.Date = new java.util.Date(millis)

  def toJodaDateTime: DateTime = new DateTime(millis)

  def toJodaDateTimeWithTimeZone(timezone: DateTimeZone): DateTime = new DateTime(millis, timezone)

  def toJodaLocalDateTime: LocalDateTime = new LocalDateTime(millis)

  def toJodaLocalDateTimeWithTimeZone(timezone: DateTimeZone): LocalDateTime = new LocalDateTime(millis, timezone)

  def toJodaLocalDate: LocalDate = new LocalDate(millis)

  def toJodaLocalDateWithTimeZone(timezone: DateTimeZone): LocalDate = new LocalDate(millis, timezone)

  def toJodaLocalTime: LocalTime = new LocalTime(millis)

  def toJodaLocalTimeWithTimeZone(timezone: DateTimeZone): LocalTime = new LocalTime(millis, timezone)

  def toSqlDate: java.sql.Date = {
    // @see http://docs.oracle.com/javase/7/docs/api/java/sql/Date.html
    // -----
    // To conform with the definition of SQL DATE,
    // the millisecond values wrapped by a java.sql.Date instance must be 'normalized'
    // by setting the hours, minutes, seconds, and milliseconds to zero
    // in the particular time zone with which the instance is associated.
    // -----
    val cal = Calendar.getInstance()
    cal.setTimeInMillis(millis)
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    new java.sql.Date(cal.getTimeInMillis)
  }

  def toSqlTime: java.sql.Time = new java.sql.Time(millis)

  def toSqlTimestamp: java.sql.Timestamp = new java.sql.Timestamp(millis)

}

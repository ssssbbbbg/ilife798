package com.github.ilife798

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitEra
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitSecond
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDateComponents
import platform.Foundation.NSTimeZone
import platform.Foundation.currentCalendar
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZone

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

private fun formatter(pattern: String): NSDateFormatter =
    NSDateFormatter().apply {
        dateFormat = pattern
    }

actual fun currentTimeFormatted(pattern: String): String = formatter(pattern).stringFromDate(NSDate())

actual fun formatTimestamp(
    timestamp: Long,
    pattern: String,
): String = formatter(pattern).stringFromDate(NSDate.dateWithTimeIntervalSince1970(timestamp / 1000.0))

actual fun getDayOfWeek(): Int {
    val cal = NSCalendar.currentCalendar
    val comps = cal.components(NSCalendarUnitWeekday, fromDate = NSDate())
    val weekday = comps.weekday.toInt() // 1=Sunday .. 7=Saturday
    return if (weekday == 1) 7 else weekday - 1
}

actual fun getTodayStart(now: Long): Long {
    val cal = NSCalendar.currentCalendar
    val date = NSDate.dateWithTimeIntervalSince1970(now / 1000.0)
    val units = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay
    val comps = cal.components(units, fromDate = date)
    comps.hour = 0
    comps.minute = 0
    comps.second = 0
    val startDate = cal.dateFromComponents(comps)!!
    return (startDate.timeIntervalSince1970 * 1000).toLong()
}
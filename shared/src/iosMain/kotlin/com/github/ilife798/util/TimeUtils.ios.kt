package com.github.ilife798.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitWeekday
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.timeIntervalSince1970

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

private fun formatter(pattern: String): NSDateFormatter =
    NSDateFormatter().apply {
        dateFormat = pattern
    }

actual fun currentTimeFormatted(pattern: String): String = formatter(pattern).stringFromDate(NSDate())

actual fun formatTimestamp(
    timestamp: Long,
    pattern: String,
): String = formatter(pattern).stringFromDate(NSDate(timeIntervalSince1970 = timestamp / 1000.0))

actual fun getDayOfWeek(): Int {
    val cal = NSCalendar.currentCalendar
    val comps = cal.components(NSCalendarUnitWeekday, fromDate = NSDate())
    val weekday = comps.weekday.toInt() // 1=Sunday .. 7=Saturday
    return if (weekday == 1) 7 else weekday - 1
}

actual fun getTodayStart(now: Long): Long {
    val cal = NSCalendar.currentCalendar
    val date = NSDate(timeIntervalSince1970 = now / 1000.0)
    val comps = cal.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = date,
    )
    comps.hour = 0
    comps.minute = 0
    comps.second = 0
    val startDate = cal.dateFromComponents(comps)!!
    return (startDate.timeIntervalSince1970 * 1000).toLong()
}

package com.project.newsnexus.utils

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TimeUtil {
    companion object{
        fun formatDateTime(dateTime: String): String{
            val utcDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
            val deviceZone = ZoneId.systemDefault()
            val deviceDateTime = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC")).withZoneSameInstant(deviceZone)
            val outputFomatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a z")
            return deviceDateTime.format(outputFomatter)
        }

        fun getTimeDiff(input: String): String {
            // Parse the input string to a LocalDateTime object
            val utcDateTime = LocalDateTime.parse(input, DateTimeFormatter.ISO_DATE_TIME)

            // Convert to the device's time zone
            val deviceZone = ZoneId.systemDefault()
            val deviceDateTime = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC")).withZoneSameInstant(deviceZone)

            // Define the desired output format
            val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a z")

            // Format the date-time object to the desired format
            val formattedDateTime = deviceDateTime.format(outputFormatter)

            // Calculate the time difference
            val now = ZonedDateTime.now(deviceZone)
            val difference = Duration.between(deviceDateTime, now)

            // Determine the appropriate time difference string
            val differenceString = when {
                difference.toDays() > 365 -> "${difference.toDays() / 365}y"
                difference.toDays() > 30 -> "${difference.toDays() / 30}mon"
                difference.toDays() > 1 -> "${difference.toDays()}d"
                difference.toHours() > 1 -> "${difference.toHours()}h"
                difference.toMinutes() > 1 -> "${difference.toMinutes()}min"
                else -> "${difference.seconds}s"
            }

            return "$differenceString ago"
        }
    }
}
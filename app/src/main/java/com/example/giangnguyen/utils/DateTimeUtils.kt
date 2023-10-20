package com.example.giangnguyen.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


object DateTimeUtils {
    fun convertMatchDateStringToDate(dateString: String): Date? {
        try {
            return convertDateStringToDate(dateString, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }


    private fun convertDateStringToDate(dateString: String, pattern: String): Date {
        val format = SimpleDateFormat(pattern)
        try {
            return format.parse(dateString)
        } catch (e: ParseException) {
            throw e
        }
    }

    fun convertMatchDateStringToOnlyDateString(dateString: String): String {
        val dateTime = convertDateStringToLocalDateTime(dateString, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return convertLocalDateTimeToString(dateTime, "EEE, d MMM yyyy");
    }

    fun convertMatchDateStringToHourMinuteString(dateString: String): String {
        val dateTime = convertDateStringToLocalDateTime(dateString, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return convertLocalDateTimeToString(dateTime, "HH:mm");
    }

    fun convertDateStringToLocalDateTime(dateString: String, pattern: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(dateString, formatter)
    }

    fun convertLocalDateTimeToString(date: LocalDateTime, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }

}
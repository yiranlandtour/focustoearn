package com.threedust.app.utils

import android.text.format.DateFormat
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    const val ONE_MINUTE_MILLIONS = 60 * 1000.toLong()
    const val ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS
    const val ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS

    fun currentTime(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun getTimestamp(): String? {
        return (System.currentTimeMillis() / 1000).toString()
    }

    /**
     *
     * @return
     */
    fun getShortTime(millis: Long): String? {
        val date = Date(millis)
        val curDate = Date()
        var str = ""
        val durTime = curDate.time - date.time
        val dayStatus = calculateDayStatus(date, Date())
        str = if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
            "刚刚"
        } else if (durTime < ONE_HOUR_MILLIONS) {
            (durTime / ONE_MINUTE_MILLIONS).toString() + "分钟前"
        } else if (dayStatus == 0) {
            (durTime / ONE_HOUR_MILLIONS).toString() + "小时前"
        } else if (dayStatus == -1) {
            "昨天" + DateFormat.format("HH:mm", date)
        } else if (isSameYear(date, curDate) && dayStatus < -1) {
            DateFormat.format("MM-dd", date).toString()
        } else {
            DateFormat.format("yyyy-MM", date).toString()
        }
        return str
    }

    /**
     * 获取完整时间格式 "2016-01-06 09:37:04"
     *
     * @return
     */
    fun getLongTime(millis: Long): String? {
        val date = Date(millis * 1000)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }

    /**
     * 判断是否是同一年
     * @param targetTime
     * @param compareTime
     * @return
     */
    fun isSameYear(targetTime: Date?, compareTime: Date?): Boolean {
        val tarCalendar = Calendar.getInstance()
        tarCalendar.time = targetTime
        val tarYear = tarCalendar[Calendar.YEAR]
        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareTime
        val comYear = compareCalendar[Calendar.YEAR]
        return tarYear == comYear
    }

    /**
     * 判断是否处于今天还是昨天，0表示今天，-1表示昨天，小于-1则是昨天以前
     * @param targetTime
     * @param compareTime
     * @return
     */
    fun calculateDayStatus(targetTime: Date?, compareTime: Date?): Int {
        val tarCalendar = Calendar.getInstance()
        tarCalendar.time = targetTime
        val tarDayOfYear = tarCalendar[Calendar.DAY_OF_YEAR]
        val compareCalendar = Calendar.getInstance()
        compareCalendar.time = compareTime
        val comDayOfYear = compareCalendar[Calendar.DAY_OF_YEAR]
        return tarDayOfYear - comDayOfYear
    }

    /**
     * 将秒数转换成00:00的字符串，如 118秒 -> 01:58
     * @param time
     * @return
     */
    fun secToTime(time: Int): String? {
        var timeStr: String? = null
        var hour = 0
        var minute = 0
        var second = 0
        if (time <= 0) return "00:00" else {
            minute = time / 60
            if (minute < 60) {
                second = time % 60
                timeStr = unitFormat(minute) + ":" + unitFormat(second)
            } else {
                hour = minute / 60
                if (hour > 99) return "99:59:59"
                minute = minute % 60
                second = time - hour * 3600 - minute * 60
                timeStr = (unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second))
            }
        }
        return timeStr
    }

    fun unitFormat(i: Int): String {
        var retStr: String? = null
        retStr = if (i >= 0 && i < 10) "0" + Integer.toString(i) else "" + i
        return retStr
    }

    fun strToDate(dateStr: String?): Date { // 字符串转date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        dateStr?.let {
            try {
                if (! it.isEmpty()) return dateFormat.parse(it)
            } catch (e: Exception) {
                try {
                    val dateFormat1 = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    if (! it.isEmpty()) return dateFormat1.parse(it)
                } catch (e: Exception) {

                }
            }
        }
        return Date(System.currentTimeMillis())
    }

    fun fullDateStr(dateStr: String?): String { // 转换成xxxx-mm-dd hh:mm格式
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val date = strToDate(dateStr)
        return dateFormat.format(date)
    }

    fun getDateStr(dateStr: String?): String { // 转换成xxxx-mm-dd格式
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = strToDate(dateStr)
        return dateFormat.format(date)
    }

    // 获取当前日期时间字符串
    fun getNowDateStr(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return dateFormat.format(date)
    }

    fun getTodayDataStr(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(date)
    }

    // 获取当前日期时间字符串 带秒
    fun getNowDateStrSS(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(date)
    }

    // 获取前一天的日期
    fun getLastDayDateStr(): String {
        val date = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return dateFormat.format(date)
    }

    // date 转字符串
    fun dateToStr(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return dateFormat.format(date)
    }

    fun getFullTimeFromMS(millis: Long): String {
        val date = Date(millis)
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
    }
}
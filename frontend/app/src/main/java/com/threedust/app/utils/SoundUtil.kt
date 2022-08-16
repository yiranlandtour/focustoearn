package com.threedust.app.utils

import android.app.Activity
import android.media.Ringtone
import android.media.RingtoneManager

class SoundUtil(var activity: Activity) {

    val listRingTone = ArrayList<Ringtone>()
    var ringtone: Ringtone? = null

    init {
        val ringtoneManager= RingtoneManager(activity)
        val cursor = ringtoneManager.cursor
        if (cursor.count > 0) ringtone = ringtoneManager.getRingtone(0)
    }

    fun playSound() {
        ringtone?.let { rt ->
            if (!rt.isPlaying) {
                rt.play()
            }
            SysUtils.postTaskDelay(Runnable {
                if(rt.isPlaying()){
                    rt.stop();
                }
            }, 10000)
        }
    }

    fun stopSound() {
        ringtone?.isPlaying()?.let {
            if (it) ringtone?.stop();
        }
    }
}
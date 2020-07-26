package com.aztekstudios.quit.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aztekstudios.quit.handlers.ServiceMaker

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                with(ServiceMaker()) {
                    makeService(context)
                }
            }
        }
    }

}
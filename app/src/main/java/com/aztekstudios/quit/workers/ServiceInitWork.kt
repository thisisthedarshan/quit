package com.aztekstudios.quit.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aztekstudios.quit.handlers.ServiceMaker

class ServiceInitWork(context: Context, workParams: WorkerParameters) :
    Worker(context, workParams) {
    val cx = context
    override fun doWork(): Result {
        with(ServiceMaker()) {
            makeService(cx)
        }
        return Result.success()
    }
}
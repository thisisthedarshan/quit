/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "ServiceInitWork.kt" is a part of the project "Quit"                                        *
 *                                                                                                          *
 *     Quit is free software: you can redistribute it and/or modify                                         *
 *     it under the terms of the GNU General Public License as published by                                 *
 *     the Free Software Foundation, either version 3 of the License, or                                    *
 *     (at your option) any later version.                                                                  *
 *                                                                                                          *
 *     Project Quit is distributed in the hope that it will be useful,                                      *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of                                       *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                        *
 *     GNU General Public License for more details.                                                         *
 *                                                                                                          *
 *     You should have received a copy of the GNU General Public License                                    *
 *     along with Project Quit.  If not, see <https://www.gnu.org/licenses/>.                               *
 *                                                                                                          *
 ************************************************************************************************************/

package com.aztekstudios.quit.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aztekstudios.quit.handlers.ServiceMaker

/**
 * Worker Class that creates the service when triggered by Work.
 */
class ServiceInitWork(context: Context, workParams: WorkerParameters) :
    Worker(context, workParams) {

    private val cx = context        // Context

    override fun doWork(): Result {
        with(ServiceMaker()) {
            bake(cx)                        // Create service
        }
        return Result.success()             // Returns Success
    }
}
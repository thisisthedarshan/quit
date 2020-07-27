/************************************************************************************************************
 *     Copyright (c) 2020. by Darshan. All rights reserved                                                  *
 *                                                                                                          *
 *     The file "HomeViewModel.kt" is a part of the project "Quit"                                          *
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

package com.aztekstudios.quit.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aztekstudios.quit.util.Helper

/**
 * ViewModel Class for Home Fragment
 */
class HomeViewModel : ViewModel() {
    var usage: MutableLiveData<String> = MutableLiveData("0")       // Usage Data
    var unlocks: MutableLiveData<String> = MutableLiveData("0")     // Unlocks Data

    /**
     * Updates the data in the ViewModel
     * @param c Context
     */
    fun updateStuff(c: Context) {
        with(Helper()) {
            usage = MutableLiveData(getTodayUsage(c))
            unlocks = MutableLiveData(getTodayUnlocks(c))
        }
    }
}
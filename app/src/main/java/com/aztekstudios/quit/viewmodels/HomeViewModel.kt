package com.aztekstudios.quit.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aztekstudios.quit.util.Helper

class HomeViewModel : ViewModel() {
    var usage: MutableLiveData<String> = MutableLiveData("0")
    var unlocks: MutableLiveData<String> = MutableLiveData("0")

    fun updateStuff(c: Context) {
        with(Helper()) {
            usage = MutableLiveData(getTodayUsage(c))
            unlocks = MutableLiveData(getTodayUnlocks(c))
        }
    }
}
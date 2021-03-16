package com.fcossetta.myapplication.main.data

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    // This is the data bundle from fragment B to A
    val bundleFromFragmentBToFragmentA = MutableLiveData<Bundle>()
}
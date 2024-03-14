package com.example.hostelworldchallenge

import android.app.Application
import android.content.Context

class HostelWorldChallengeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {
        var instance: HostelWorldChallengeApplication? = null
        fun getContext(): Context? {
            return instance?.applicationContext
        }
    }
}
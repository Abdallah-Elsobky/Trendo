package iti.gov.trendo

import android.app.Application
import iti.gov.trendo.data.local.db.DatabaseFactory

class TrendoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        DatabaseFactory.initialize(this)
    }
}
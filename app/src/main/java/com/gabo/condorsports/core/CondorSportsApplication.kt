package com.gabo.condorsports.core

import android.app.Application
import com.gabo.condorsports.data.network.RetrofitBuilder
import com.gabo.condorsports.data.repository.*

class CondorSportsApplication : Application() {

    val teamRepository : TeamRepository by lazy {
        TeamRepositoryImpl(RetrofitBuilder.teamAPIService)
    }

    val teamEventsRepository : TeamEventsRepository by lazy {
        TeamEventsRepositoryImpl(RetrofitBuilder.teamAPIService)
    }

    val leagueRepository : LeagueRepository by lazy {
        LeagueRepositryImpl(RetrofitBuilder.teamAPIService)
    }


}
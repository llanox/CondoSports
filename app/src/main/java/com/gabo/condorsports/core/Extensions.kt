package com.gabo.condorsports.core

import android.content.Context
import com.gabo.condorsports.data.repository.LeagueRepository
import com.gabo.condorsports.data.repository.TeamEventsRepository
import com.gabo.condorsports.data.repository.TeamRepository

fun Context.provideTeamRepository(): TeamRepository {
    val poCApplication = applicationContext as CondorSportsApplication
    return poCApplication.teamRepository
}

fun Context.provideEventsRepository(): TeamEventsRepository {
    val poCApplication = applicationContext as CondorSportsApplication
    return poCApplication.teamEventsRepository
}

fun Context.provideLeagueRepository(): LeagueRepository {
    val poCApplication = applicationContext as CondorSportsApplication
    return poCApplication.leagueRepository
}
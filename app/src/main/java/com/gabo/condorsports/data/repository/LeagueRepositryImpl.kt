package com.gabo.condorsports.data.repository

import com.gabo.condorsports.data.model.League
import com.gabo.condorsports.data.network.TeamAPIService
import timber.log.Timber

class LeagueRepositryImpl(private val teamAPIService: TeamAPIService) : LeagueRepository {
    override suspend fun fetchLeagues(): Response<List<League>>? {
        return try {
            val leagues = teamAPIService.fetchLeagues().leagues
            Response.Success(leagues)
        } catch (ex: Throwable) {
            Timber.e(ex, "Error fetching team details")
            Response.Error(exception = ex)
        }
    }

}
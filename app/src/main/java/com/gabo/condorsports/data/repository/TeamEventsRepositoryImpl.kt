package com.gabo.condorsports.data.repository

import com.gabo.condorsports.data.model.Event
import com.gabo.condorsports.data.network.TeamAPIService
import timber.log.Timber

class TeamEventsRepositoryImpl(private val teamAPIService: TeamAPIService) : TeamEventsRepository {
    override suspend fun fetchAllTeamEvents(teamId: String): Response<List<Event>> {
        return try {
            val events = teamAPIService.fetchEventsByTeamId(teamId).events
            Response.Success(events)
        } catch (ex: Throwable) {
            Timber.e(ex, "Error fetching team details")
            Response.Error(exception = ex)
        }    }
}
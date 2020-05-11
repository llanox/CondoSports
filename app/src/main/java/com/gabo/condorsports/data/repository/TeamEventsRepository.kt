package com.gabo.condorsports.data.repository

import com.gabo.condorsports.data.model.Event

interface TeamEventsRepository {
    suspend fun fetchAllTeamEvents(teamId: String): Response<List<Event>>?

}
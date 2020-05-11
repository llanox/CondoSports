package com.gabo.condorsports.data.repository

import com.gabo.condorsports.data.model.Team

interface TeamRepository {
    suspend fun fetchTeamDetails(teamId: String): Response<Team>
    suspend fun fetchAllTeamsByLeagueId(leagueId: String): Response<List<Team>>?

}

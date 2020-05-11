package com.gabo.condorsports.data.repository

import com.gabo.condorsports.data.model.Team
import com.gabo.condorsports.data.network.TeamAPIService
import timber.log.Timber

class TeamRepositoryImpl(private val teamAPIService: TeamAPIService) : TeamRepository {


    override suspend fun fetchTeamDetails(id: String): Response<Team> {
        return try {
            val teams = teamAPIService.fetchTeamDetailsById(id).teams
            Response.Success(teams.first())
        } catch (ex: Throwable) {
            Timber.e(ex, "Error fetching team details")
            Response.Error(exception = ex)
        }
    }

    override suspend fun fetchAllTeamsByLeagueId(leagueId: String): Response<List<Team>> {
        return try {
            Response.Success(teamAPIService.fetchAllTeamsByLeagueId(leagueId).teams)
        } catch (ex: Throwable) {
            Timber.e(ex, "Error fetching list of teams for league $leagueId")
            Response.Error(exception = ex)
        }
    }
}
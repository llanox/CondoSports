package com.gabo.condorsports.data.network

import com.gabo.condorsports.data.model.EventsResponse
import com.gabo.condorsports.data.model.LeaguesResponse
import com.gabo.condorsports.data.model.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamAPIService{

    @GET("lookup_all_teams.php")
    suspend fun fetchAllTeamsByLeagueId(@Query("id") leagueId: String) : TeamsResponse

    @GET("lookupteam.php")
    suspend fun fetchTeamDetailsById(@Query("id") teamId: String): TeamsResponse

    @GET("eventsnext.php")
    suspend fun fetchEventsByTeamId(@Query("id") teamId: String): EventsResponse

    @GET("all_leagues.php")
    suspend fun fetchLeagues(): LeaguesResponse

}
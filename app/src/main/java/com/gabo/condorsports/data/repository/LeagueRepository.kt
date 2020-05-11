package com.gabo.condorsports.data.repository

import com.gabo.condorsports.data.model.League

interface LeagueRepository {

    suspend fun fetchLeagues(): Response<List<League>>?

}
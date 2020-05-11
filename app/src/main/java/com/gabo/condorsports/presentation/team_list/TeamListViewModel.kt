package com.gabo.condorsports.presentation.team_list

import androidx.lifecycle.*
import com.gabo.condorsports.core.DEFAULT_LEAGUE_ID
import com.gabo.condorsports.data.repository.LeagueRepository
import com.gabo.condorsports.data.repository.TeamRepository
import com.gabo.condorsports.presentation.ScreenState
import com.gabo.condorsports.data.repository.Response
import com.gabo.condorsports.presentation.Action
import kotlinx.coroutines.Dispatchers


class TeamListViewModel(private val teamRepository: TeamRepository, private  val leagueRepository: LeagueRepository) : ViewModel() {


    fun findTeams(leagueId : String = DEFAULT_LEAGUE_ID) =  liveData(Dispatchers.IO) {
        emit(ScreenState.Loading())
        when(val result = teamRepository.fetchAllTeamsByLeagueId(leagueId)){
                is Response.Success ->  emit(ScreenState.Render(result.data, Action.RENDER_TEAMS))
                is Response.Error -> emit(ScreenState.Error(message = "Error fetching teams for the league $leagueId",data = result.exception))
            }
        }

    fun findLeagues() =  liveData(Dispatchers.IO) {
        emit(ScreenState.Loading())
        when(val result = leagueRepository.fetchLeagues()){
            is Response.Success ->  emit(ScreenState.Render(result.data, Action.RENDER_LEAGUES))
            is Response.Error -> emit(ScreenState.Error(message = "Error fetching leagues",data = result.exception))
        }
    }

}

class TeamListViewModelFactory(private val teamRepository: TeamRepository, private  val leagueRepository: LeagueRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamListViewModel(
            teamRepository,
            leagueRepository
        ) as T
    }
}
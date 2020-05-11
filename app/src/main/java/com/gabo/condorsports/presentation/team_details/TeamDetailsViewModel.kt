package com.gabo.condorsports.presentation.team_details

import androidx.lifecycle.*
import com.gabo.condorsports.data.repository.TeamRepository
import com.gabo.condorsports.presentation.ScreenState
import com.gabo.condorsports.data.model.Team
import com.gabo.condorsports.data.repository.Response
import com.gabo.condorsports.data.repository.TeamEventsRepository
import com.gabo.condorsports.presentation.Action
import kotlinx.coroutines.Dispatchers

class TeamDetailsViewModel(private val teamRepository: TeamRepository, private val eventsRepository: TeamEventsRepository) : ViewModel() {

    fun findTeamDetails(teamId: String) = liveData(context= Dispatchers.IO) {
        emit(ScreenState.Loading())
         when(val result = teamRepository.fetchTeamDetails(teamId)){
            is Response.Success -> emit(ScreenState.Render(result.data, Action.RENDER_TEAM_DETAILS))
            is Response.Error ->  emit(ScreenState.Error(message = "Error fetching list of teams", data = result.exception))
         }

    }


    fun findTeamEvents(teamId: String) = liveData(context= Dispatchers.IO) {
        emit(ScreenState.Loading())
        when(val result = eventsRepository.fetchAllTeamEvents(teamId)){
            is Response.Success -> emit(ScreenState.Render(result.data, Action.RENDER_TEAM_EVENTS))
            is Response.Error ->  emit(ScreenState.Error(message = "Error fetching list of teams", data = result.exception))
        }

    }


}

class TeamDetailsViewModelFactory(private val teamRepository: TeamRepository , private  val eventsRepository: TeamEventsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeamDetailsViewModel(
            teamRepository,
            eventsRepository
        ) as T
    }
}

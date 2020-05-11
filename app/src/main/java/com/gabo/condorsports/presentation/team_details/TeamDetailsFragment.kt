package com.gabo.condorsports.presentation.team_details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gabo.condorsports.R
import com.gabo.condorsports.core.TEAM_ID_KEY
import com.gabo.condorsports.core.provideEventsRepository
import com.gabo.condorsports.core.provideTeamRepository
import com.gabo.condorsports.data.model.Event
import com.gabo.condorsports.data.model.Team
import com.gabo.condorsports.databinding.TeamDetailsBinding
import com.gabo.condorsports.presentation.Action
import com.gabo.condorsports.presentation.ScreenState
import com.gabo.condorsports.presentation.team_list.TeamRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar

class TeamDetailsFragment : Fragment() {
    private var _binding: TeamDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TeamDetailsViewModel
    private lateinit var eventAdapter: TeamEventRecyclerViewAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TeamDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.teamDescription.movementMethod = ScrollingMovementMethod()
        binding.upcomingEventList.visibility = View.GONE

        context?.apply {
                val factory = TeamDetailsViewModelFactory(provideTeamRepository(), provideEventsRepository())
                viewModel = ViewModelProvider(this@TeamDetailsFragment, factory).get(TeamDetailsViewModel::class.java)
                arguments?.get(TEAM_ID_KEY)?.let { teamId ->
                    viewModel.findTeamDetails(teamId.toString()).observe(viewLifecycleOwner, Observer(::renderState))
                    viewModel.findTeamEvents(teamId.toString()).observe(viewLifecycleOwner, Observer(::renderState))

                }
        }

        eventAdapter = TeamEventRecyclerViewAdapter(emptyList())
        binding.upcomingEventList.apply {
            adapter = eventAdapter
        }
    }

    private fun renderState(screenState: ScreenState<Any>) {

        when (screenState) {
            is ScreenState.Render -> renderView(screenState)
            is ScreenState.Error -> showError(screenState.message)
            is ScreenState.Loading -> showLoading()
        }

    }

    private fun renderView(state: ScreenState.Render<Any>) {
        when(state.action){
            Action.RENDER_TEAM_DETAILS ->  state.data?.let { showTeamDetails(state.data as Team)}
            Action.RENDER_TEAM_EVENTS -> state.data?.let { showEvents( state.data as List<Event>) }
        }
    }

    private fun showEvents(data: List<Event>) {
        if(!data.isNullOrEmpty()){
            binding.upcomingEventList.visibility = View.VISIBLE
            eventAdapter.update(data)
        }

    }

    private fun showLoading() {
        Snackbar.make(this.requireView(), "Loading...", Snackbar.LENGTH_LONG).show()
    }

    private fun showError(message: String?) {
        binding.teamEmptyState.visibility = View.VISIBLE
        binding.teamEmptyState.setAnimation(R.raw.went_wrong)
        binding.teamEmptyState.playAnimation()
        binding.teamEmptyState.repeatCount = 0

        message?.let {
            Snackbar.make(this.requireView(), message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showTeamDetails(data: Team?) {
        binding.teamDetailsGroup.visibility = View.INVISIBLE
        binding.teamName.text = data?.name
        binding.teamDescription.text = data?.description
        binding.formedYear.text = data?.formedYear

        context?.let {
            Glide.with(it)
                .load(data?.urlBadgeImage)
                .thumbnail(0.1f)
                .into(binding.teamBadge)
            Glide.with(it)
                .load(data?.urlJerseyImage)
                .thumbnail(0.1f)
                .into(binding.teamJersey)
        }
        binding.teamDetailsGroup.visibility = View.VISIBLE
        binding.teamEmptyState.visibility = View.GONE

        if (!data?.youtubeChannel.isNullOrEmpty()) {
            binding.youtubeLink.text = data?.youtubeChannel
        } else {
            binding.rowYoutube.visibility = View.GONE
        }

    }
}
package com.gabo.condorsports.presentation.team_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.condorsports.R
import com.gabo.condorsports.core.DEFAULT_LEAGUE_ID
import com.gabo.condorsports.core.TEAM_ID_KEY
import com.gabo.condorsports.core.provideLeagueRepository
import com.gabo.condorsports.core.provideTeamRepository
import com.gabo.condorsports.data.model.League
import com.gabo.condorsports.data.model.Team
import com.gabo.condorsports.databinding.TeamListBinding
import com.gabo.condorsports.presentation.Action
import com.gabo.condorsports.presentation.ScreenState
import com.google.android.material.snackbar.Snackbar


class TeamListFragment : Fragment(), TeamRecyclerViewAdapter.OnListInteractionListener {

    private var _binding: TeamListBinding? = null
    private lateinit var teamAdapter: TeamRecyclerViewAdapter
    private lateinit var leaguesAdapter: LeaguesDropDownAdapter
    private val binding get() = _binding!!


    private lateinit var viewModel: TeamListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TeamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.apply {

            val factory =
                TeamListViewModelFactory(
                    teamRepository = provideTeamRepository(),
                    leagueRepository = provideLeagueRepository()
                )
            viewModel = ViewModelProvider(this@TeamListFragment, factory).get(TeamListViewModel::class.java)
            viewModel.findTeams().observe(viewLifecycleOwner, Observer(::renderState))
            viewModel.findLeagues().observe(viewLifecycleOwner, Observer(::renderState))
            leaguesAdapter = LeaguesDropDownAdapter(this, emptyList())
        }

        teamAdapter = TeamRecyclerViewAdapter(emptyList(), this)

        binding.teamsListRecycler.apply {
            adapter = teamAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.leagueDropdownList.apply {
            adapter = leaguesAdapter
        }
        binding.leagueDropdownList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = binding.leagueDropdownList.selectedItem as League
                viewModel.findTeams(selected.id).observe(viewLifecycleOwner, Observer(::renderState))
            }

        }


    }

    private fun renderState(screenState: ScreenState<Any>) {

        when (screenState) {
            is ScreenState.Render -> renderView(screenState) //showTeams(screenState.data as List<Team>)
            is ScreenState.Error -> showError(screenState.message)
            is ScreenState.Loading -> showLoading()
        }

    }

    private fun renderView(state: ScreenState.Render<Any>) {
        when(state.action){
            Action.RENDER_TEAMS -> showTeams(state.data as List<Team>)
            Action.RENDER_LEAGUES -> showLeagues(state.data as List<League>)
        }
    }

    private fun showLeagues(leagues: List<League>) {
        if (!leagues.isNullOrEmpty()) {
            leaguesAdapter.update(leagues)
        }

        binding.leagueDropdownList.setSelection(leaguesAdapter.getPosition(DEFAULT_LEAGUE_ID),false)

    }


    private fun showTeams(teams: List<Team>?) {
        if (teams.isNullOrEmpty()){
            binding.teamListEmptyState.visibility = View.VISIBLE
        }else {
            binding.teamListEmptyState.visibility = View.GONE
            teams.let {
                teamAdapter.updateTeams(it)
            }
        }


    }

    private fun showError(message: String?) {
        binding.teamListEmptyState.setAnimation(R.raw.went_wrong)
        binding.teamListEmptyState.playAnimation()
        binding.teamListEmptyState.repeatCount = 0
        message ?.let {
            Snackbar.make(this.requireView(),message,Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showLoading() {
        Snackbar.make(this.requireView(),"Loading...",Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onListItemSelected(item: Team) {
        val bundle = bundleOf(TEAM_ID_KEY to item.id)
        findNavController().navigate(R.id.action_from_team_list_to_team_details, bundle)
    }

}
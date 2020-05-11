package com.gabo.condorsports.data.model

import com.google.gson.annotations.SerializedName

data class Team (
    @SerializedName("idTeam")
    val id: String,
    @SerializedName("strTeam")
    val name: String? = null,
    @SerializedName("strTeamBadge")
    val urlBadgeImage: String? = null,
    @SerializedName("strStadium")
    val stadium: String? = null,
    @SerializedName("strTeamJersey")
    val urlJerseyImage: String? =null,
    @SerializedName("intFormedYear")
    val formedYear: String? =null,
    @SerializedName("strDescriptionEN")
    val description: String? =null,
    @SerializedName("strYoutube")
    val youtubeChannel: String? =null
)

data class TeamsResponse (
    @SerializedName("teams")
    val teams: List<Team> = emptyList()
)




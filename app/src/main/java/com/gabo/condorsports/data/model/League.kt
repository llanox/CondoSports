package com.gabo.condorsports.data.model

import com.google.gson.annotations.SerializedName

data class LeaguesResponse (
    @SerializedName("leagues")
    val leagues: List<League> = emptyList()
)

data class League (
    @SerializedName("idLeague")
    val id : String,
    @SerializedName("strLeague")
    val name: String? =null)

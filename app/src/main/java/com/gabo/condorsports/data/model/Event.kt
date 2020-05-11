package com.gabo.condorsports.data.model

import com.google.gson.annotations.SerializedName

data class EventsResponse (
    @SerializedName("events")
    val events: List<Event> = emptyList()
)

data class Event (
    @SerializedName("idEvent")
    val id : String,
    @SerializedName("strEvent")
    val eventName: String? =null)
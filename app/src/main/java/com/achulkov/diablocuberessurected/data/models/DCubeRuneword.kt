package com.achulkov.diablocuberessurected.data.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DCubeRuneword(
    var name : String = "",
    var inputs : List<String> = listOf(),
    var levelRequirement: String = "",
    var inputBaseItem : String = "",
    var stats : List<String> = listOf()
)

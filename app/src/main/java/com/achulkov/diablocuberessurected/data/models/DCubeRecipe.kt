package com.achulkov.diablocuberessurected.data.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DCubeRecipe(
    var name : String = "",
    var inputs : List<DCubeInput> = listOf(),
    var output : String = ""
)

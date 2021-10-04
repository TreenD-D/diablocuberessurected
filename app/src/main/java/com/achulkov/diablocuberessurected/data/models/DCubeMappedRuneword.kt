package com.achulkov.diablocuberessurected.data.models

data class DCubeMappedRuneword(
    var name : String = "",
    var inputs : List<DCubeItem> = listOf(),
    var levelRequirement: String = "",
    var inputBaseItem : String = "",
    var stats : List<String> = listOf()
)

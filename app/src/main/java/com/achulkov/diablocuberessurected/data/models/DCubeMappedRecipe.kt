package com.achulkov.diablocuberessurected.data.models

data class DCubeMappedRecipe(
    var name : String = "",
    var inputs : List<DCubeMappedInput> = listOf(),
    var output : DCubeItem = DCubeItem()
)

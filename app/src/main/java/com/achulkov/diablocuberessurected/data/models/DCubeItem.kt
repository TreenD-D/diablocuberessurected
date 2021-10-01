package com.achulkov.diablocuberessurected.data.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DCubeItem(
    var image: String = "",
    var inputOf: List<String> = listOf(),
    var resultOf: List<String> = listOf(),
    var itemdesc: String = "",
    var itemname: String = "",
    var stats: List<String> = listOf(),
    var variants: List<String> = listOf()
)

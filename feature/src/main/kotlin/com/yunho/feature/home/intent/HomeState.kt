package com.yunho.feature.home.intent

data class HomeState(
    val data: String
) {
    constructor() : this(data = "")
}
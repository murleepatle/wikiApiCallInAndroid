package com.murlipatle.wikiapitesty.retrofit.responce


import com.google.gson.annotations.SerializedName

data class Continue(
    @SerializedName("accontinue")
    val accontinue: String,
    @SerializedName("continue")
    val continueX: String
)
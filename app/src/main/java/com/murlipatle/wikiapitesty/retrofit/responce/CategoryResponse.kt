package com.murlipatle.wikiapitesty.retrofit.responce


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("batchcomplete")
    val batchcomplete: Boolean,
    @SerializedName("continue")
    val continueX: Continue,
    @SerializedName("query")
    val query: Query
)
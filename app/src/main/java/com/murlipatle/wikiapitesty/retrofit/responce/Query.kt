package com.murlipatle.wikiapitesty.retrofit.responce


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("allcategories")
    val allcategories: List<Allcategory>
)
package com.murlipatle.wikiapitesty.retrofit.responce


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class Allcategory(
    @SerializedName("category")
    @PrimaryKey
    val category: String
)
package com.murlipatle.wikiapitesty.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pageId: Int,
    val title: String,
    val description: String,
    val images: String
)
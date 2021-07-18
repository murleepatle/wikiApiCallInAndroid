package com.murlipatle.wikiapitesty.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val next: String?
)
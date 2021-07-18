package com.murlipatle.wikiapitesty.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val next: String?
)
package com.anenigmatic.secupia.screens.visitors.core

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

/**
 * Represents a visitor.
 * */
@Entity(tableName = "Visitors")
data class Visitor(
    @PrimaryKey val id: Long,
    val name: String,
    val datetime: LocalDateTime,
    val phoneNo: String,
    val purposeOfVisit: String
)
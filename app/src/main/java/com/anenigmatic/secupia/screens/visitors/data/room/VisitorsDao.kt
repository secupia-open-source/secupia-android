package com.anenigmatic.secupia.screens.visitors.data.room

import androidx.room.*
import com.anenigmatic.secupia.screens.visitors.core.Visitor
import io.reactivex.Flowable

@Dao
interface VisitorsDao {

    @Query("SELECT * FROM Visitors")
    fun getAllVisitors(): Flowable<List<Visitor>>

    @Query("SELECT * FROM Visitors WHERE id = :id")
    fun getVisitorById(id: Long): Flowable<Visitor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVisitor(visitor: Visitor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVisitors(visitors: List<Visitor>)

    @Update
    fun updateVisitor(visitor: Visitor)

    @Query("DELETE FROM Visitors WHERE id = :id")
    fun deleteVisitorById(id: Long)

    @Query("DELETE FROM Visitors")
    fun deleteAllVisitors()
}
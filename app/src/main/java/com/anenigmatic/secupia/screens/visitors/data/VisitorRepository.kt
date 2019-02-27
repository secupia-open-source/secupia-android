package com.anenigmatic.secupia.screens.visitors.data

import com.anenigmatic.secupia.screens.visitors.core.Visitor
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Used for accessing visitor details and performing all visitor related actions.
 * */
interface VisitorRepository {

    /**
     * Gives the list of all expected visitors sorted in ascending order by date.
     * */
    fun getAllVisitors(): Flowable<List<Visitor>>

    /**
     * Gives the visitor whose id matches the passed-in id.
     * */
    fun getVisitorById(id: Long): Flowable<Visitor>

    /**
     * Inserts the passed-in  visitor into the data source.
     * */
    fun insertVisitor(visitor: Visitor): Completable

    /**
     * Updates the visitor having the same id as the passed-in visitor's.
     * */
    fun updateVisitor(visitor: Visitor): Completable

    /**
     * Deletes the visitor having the passed-in id.
     * */
    fun deleteVisitorById(id: Long): Completable

    /**
     * Fetches the updated list of visitors from the central data source.
     * */
    fun refreshVisitors(): Completable
}
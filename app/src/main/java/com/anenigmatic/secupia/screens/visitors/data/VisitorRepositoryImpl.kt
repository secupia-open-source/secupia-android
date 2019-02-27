package com.anenigmatic.secupia.screens.visitors.data

import com.anenigmatic.secupia.screens.visitors.core.Visitor
import com.anenigmatic.secupia.screens.visitors.data.room.VisitorsDao
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class VisitorRepositoryImpl(private val vDao: VisitorsDao) : VisitorRepository {

    override fun getAllVisitors(): Flowable<List<Visitor>> {
        return vDao.getAllVisitors()
    }

    override fun getVisitorById(id: Long): Flowable<Visitor> {
        return vDao.getVisitorById(id)
    }

    override fun insertVisitor(visitor: Visitor): Completable {
        return Completable.create { emitter ->
            try {
                vDao.insertVisitor(visitor)
                emitter.onComplete()
            } catch(e: Exception) {
                emitter.onError(e)
            }
        }
            .delay(1, TimeUnit.SECONDS)
    }

    override fun updateVisitor(visitor: Visitor): Completable {
        return Completable.create { emitter ->
            try {
                vDao.updateVisitor(visitor)
                emitter.onComplete()
            } catch(e: Exception) {
                emitter.onError(e)
            }
        }
            .delay(1, TimeUnit.SECONDS)
    }

    override fun deleteVisitorById(id: Long): Completable {
        return Completable.create { emitter ->
            try {
                vDao.deleteVisitorById(id)
                emitter.onComplete()
            } catch(e: Exception) {
                emitter.onError(e)
            }
        }
            .delay(1, TimeUnit.SECONDS)
    }

    override fun refreshVisitors(): Completable {
        return Completable.complete()
            .delay(1, TimeUnit.SECONDS)
    }
}
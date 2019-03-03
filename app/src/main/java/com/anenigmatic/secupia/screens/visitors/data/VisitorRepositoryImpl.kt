package com.anenigmatic.secupia.screens.visitors.data

import com.anenigmatic.secupia.screens.shared.data.UserRepository
import com.anenigmatic.secupia.screens.shared.util.toRequestBody
import com.anenigmatic.secupia.screens.visitors.core.Visitor
import com.anenigmatic.secupia.screens.visitors.data.retrofit.VisitorService
import com.anenigmatic.secupia.screens.visitors.data.room.VisitorsDao
import io.reactivex.Completable
import io.reactivex.Flowable
import org.json.JSONObject
import org.threeten.bp.LocalDateTime

class VisitorRepositoryImpl(
    private val uRepo: UserRepository,
    private val vDao: VisitorsDao,
    private val vService: VisitorService
) : VisitorRepository {

    override fun getAllVisitors(): Flowable<List<Visitor>> {
        return vDao.getAllVisitors()
    }

    override fun getVisitorById(id: Long): Flowable<Visitor> {
        return vDao.getVisitorById(id)
    }

    override fun insertVisitor(visitor: Visitor): Completable {
        return uRepo.getUser()
            .firstOrError()
            .flatMap { user ->
                val body = JSONObject().apply {
                    put("name", visitor.name)
                    put("purpose", visitor.purposeOfVisit)
                    put("contact", visitor.phoneNo)
                    put("timestamp", visitor.datetime.toString())
                }
                vService.insertVisitor(user.jwt, body.toRequestBody())
            }
            .doOnSuccess { response ->
                vDao.insertVisitor(visitor.copy(id = response.id))
            }
            .ignoreElement()
    }

    override fun updateVisitor(visitor: Visitor): Completable {
        return uRepo.getUser()
            .firstOrError()
            .flatMapCompletable { user ->
                val body = JSONObject().apply {
                    put("guest_id", visitor.id)
                    put("name", visitor.name)
                    put("purpose", visitor.purposeOfVisit)
                    put("contact", visitor.phoneNo)
                    put("timestamp", visitor.datetime.toString())
                }
                vService.updateVisitor(user.jwt, body.toRequestBody())
            }
            .doOnComplete {
                vDao.updateVisitor(visitor)
            }
    }

    override fun deleteVisitorById(id: Long): Completable {
        return uRepo.getUser()
            .firstOrError()
            .flatMapCompletable { user ->
                val body = JSONObject().apply {
                    put("guest_id", id)
                }
                vService.deleteVisitor(user.jwt, body.toRequestBody())
            }
            .doOnComplete {
                vDao.deleteVisitorById(id)
            }
    }

    override fun refreshVisitors(): Completable {
        return uRepo.getUser()
            .firstOrError()
            .flatMap { user ->
                vService.getExpectedVisitors(user.jwt)
            }
            .map { response ->
                response.map {
                    Visitor(it.id, it.profile.name, LocalDateTime.parse(it.datetime.dropLast(8)), it.profile.registrationNo, it.purpose)
                }
            }
            .doOnSuccess { visitors ->
                vDao.deleteAllVisitors()
                vDao.insertVisitors(visitors)
            }
            .ignoreElement()
    }
}
package com.anenigmatic.secupia.screens.shared.data

import com.anenigmatic.secupia.screens.shared.core.User
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Used for accessing  user details and  performing all user related actions.
 * */
interface UserRepository {

    /**
     * Gives the user's details.
     *
     * @param forceUpdate  forces the app to fetch new user data. By default,
     * the app may or may not  fetch new data every time this method is used.
     * */
    fun getUser(forceUpdate: Boolean = false): Flowable<User>

    /**
     * Logs in the user with the passed in credentials.
     * */
    fun login(username: String, password: String): Completable

    /**
     * Requests backend  to reset the  user's password.
     * */
    fun resetPassword(username: String): Completable

    /**
     * Logs out the currently logged-in user. Completable.error() is returned
     * if no user was logged-in.
     * */
    fun logout(): Completable
}
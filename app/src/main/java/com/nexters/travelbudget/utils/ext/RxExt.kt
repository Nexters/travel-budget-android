package com.nexters.travelbudget.utils.ext

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * RxJava 관련 Extension methods
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.18
 */

fun <T> Single<T>.applySchedulers(): Single<T> = applyScheduler(Schedulers.io())

private fun <T> Single<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.applySchedulers(): Observable<T> = applyScheduler(Schedulers.io())

private fun <T> Observable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
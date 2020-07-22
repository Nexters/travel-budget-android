package com.nexters.travelbudget.utils.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * SingleLiveEvent
 *
 * 한번의 데이터 전달을 보장해주는 클래스
 *
 * 데이터를 전달할 필요가 없다면 call()을 통한 호출
 *
 */

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}
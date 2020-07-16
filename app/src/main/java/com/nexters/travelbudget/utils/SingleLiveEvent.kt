package com.nexters.travelbudget.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * SingleLiveEvent
 *
 * 정보 전달이 필요 없는 경우에 사용
 *
 * setValue를 통한 호출이 아닌 call()을 이용한 호출
 *
 */

class SingleLiveEvent : LiveData<Unit>() {
    private val pending = AtomicBoolean()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in Unit>) {
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(value: Unit?) {
        pending.set(true)
        super.setValue(null)
    }

    @MainThread
    fun call() {
        value = null
    }
}
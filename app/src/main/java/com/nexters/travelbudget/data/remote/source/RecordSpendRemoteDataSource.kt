package com.nexters.travelbudget.data.remote.source

import com.nexters.travelbudget.data.remote.api.TripieService
import com.nexters.travelbudget.data.remote.model.request.RecordPaymentRequest
import io.reactivex.Completable

class RecordSpendRemoteDataSource(private val tripieService: TripieService) {
    fun recordPayments(recordPaymentRequest: RecordPaymentRequest): Completable {
        return tripieService.recordPayments(recordPaymentRequest)
    }

    fun modifyPayments(paymentId: Long, recordPaymentRequest: RecordPaymentRequest): Completable {
        return tripieService.modifyPayments(paymentId, recordPaymentRequest)
    }

    fun removePayments(paymentId: Long): Completable {
        return tripieService.removePayments(paymentId)
    }
}
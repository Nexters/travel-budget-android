package com.nexters.travelbudget.data.repository

import com.nexters.travelbudget.data.remote.model.request.RecordPaymentRequest
import com.nexters.travelbudget.data.remote.source.RecordSpendRemoteDataSource
import io.reactivex.Completable

class RecordSpendRepository(private val recordSpendRemoteDataSource: RecordSpendRemoteDataSource) {
    fun recordPayments(recordPaymentRequest: RecordPaymentRequest): Completable {
        return recordSpendRemoteDataSource.recordPayments(recordPaymentRequest)
    }

    fun modifyPayments(paymentId: Long, recordPaymentRequest: RecordPaymentRequest): Completable {
        return recordSpendRemoteDataSource.modifyPayments(paymentId, recordPaymentRequest)
    }

    fun removePayments(paymentId: Long): Completable {
        return recordSpendRemoteDataSource.removePayments(paymentId)
    }
}
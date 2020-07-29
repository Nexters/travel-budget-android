package com.nexters.travelbudget.ui.select_date

import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

class SelectDateViewModel : BaseViewModel() {
    val dismissEvent = SingleLiveEvent<Unit>()

    fun dismiss() {
        dismissEvent.call()
    }
}
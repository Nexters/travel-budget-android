package com.nexters.travelbudget.ui.manage_member

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nexters.travelbudget.ui.base.BaseViewModel
import com.nexters.travelbudget.utils.lifecycle.SingleLiveEvent

/**
 * 친구 내보내기 클릭시 등장하는 안내문구 관련 뷰모델
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.16
 */
class OutMemberNoticeViewModel(username: String, private val memberId: Long) : BaseViewModel() {

    private val _username: MutableLiveData<String> = MutableLiveData(username)
    val username: LiveData<String> = _username

    private val _deleteMember: SingleLiveEvent<Long> = SingleLiveEvent()
    val deleteMember: SingleLiveEvent<Long> = _deleteMember

    private val _dismissDialog: SingleLiveEvent<Unit> = SingleLiveEvent()
    val dismissDialog: SingleLiveEvent<Unit> = _dismissDialog

    fun deleteMember() {
        _deleteMember.value = memberId
    }

    fun dismissDialog() {
        _dismissDialog.call()
    }
}
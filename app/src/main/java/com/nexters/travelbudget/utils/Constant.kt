package com.nexters.travelbudget.utils

/**
 * Intent, Arguments, ACTION_VIEW 등의 키값을 설정하는 곳
 *
 * Intent -> EXTRA_XXX
 * Fragment Arguments -> ARGUMENT_XXX
 * SharedPreferences -> PREF_XXX
 * ACTION_VIEW -> ACTION_VIEW_XXX
 * onActivityResult request code -> REQUEST_CODE_XXX
 * onActivityResult result code -> RESULT_CODE_XXX
 * ...
 *
 * @author Wayne
 * @since v1.0.0 / 2020.07.18
 */
interface Constant {

    companion object {

        // Intent
        const val EXTRA_USER_NAME = "EXTRA_USER_NAME"
        const val EXTRA_ROOM_TYPE = "EXTRA_ROOM_TYPE"
        const val EXTRA_USER_INFO = "EXTRA_USER_INFO"

        const val EXTRA_SHARED_BUDGET_ID = "EXTRA_SHARED_BUDGET_ID"
        const val EXTRA_PERSONAL_BUDGET_ID = "EXTRA_PERSONAL_BUDGET_ID"

        const val EXTRA_PLAN_ID = "EXTRA_PLAN_ID"
        const val EXTRA_MEMBER_ID = "EXTRA_MEMBER_ID"
        const val EXTRA_IS_PUBLIC = "EXTRA_IS_PUBLIC"
        const val EXTRA_ROOM_TITLE = "EXTRA_ROOM_TITLE"
        const val EXTRA_ROOM_CODE = "EXTRA_ROOM_CODE"
        const val EXTRA_PLAN_DATES = "EXTRA_PLAN_DATES"
        const val EXTRA_PLAN_START_DATE = "EXTRA_PLAN_START_DATE"
        const val EXTRA_PLAN_END_DATE = "EXTRA_PLAN_END_DATE"

        const val EXTRA_EDIT_MODE = "EXTRA_EDIT_MODE"
        const val EXTRA_BUDGET_ID = "EXTRA_BUDGET_ID"
        const val EXTRA_PAYMENT_ID = "EXTRA_PAYMENT_ID"

        const val EXTRA_CURRENT_DATE = "EXTRA_CURRENT_DATE"
        const val EXTRA_FOCUS_TYPE = "EXTRA_FOCUS_TYPE"

        const val EXTRA_ACTIVITY_RESULT_TYPE= "EXTRA_ACTIVITY_RESULT_TYPE"

        const val EXTRA_PAYMENT_TITLE = "EXTRA_PAYMENT_TITLE"
        const val EXTRA_PAYMENT_AMOUNT = "EXTRA_PAYMENT_AMOUNT"
        const val EXTRA_PAYMENT_CATEGORY = "EXTRA_PAYMENT_CATEGORY"

        // SharedPreferences
        const val PREF_NAME = "TRAVEL_BUDGET_PREF"
        const val PREF_USER_TOKEN = "PREF_USER_TOKEN"

        // Request code
        const val REQUEST_CODE_CREATE_ROOM = 20
        const val REQUEST_CODE_ENTER_ROOM = 30
        const val REQUEST_CODE_EDIT_USER_NAME = 40
        const val REQUEST_CODE_EDIT_TRIP_PROFILE = 50
        const val REQUEST_CODE_TRIP_DETAIL = 60

        const val REQUEST_CODE_SPEND_CREATE = 100
        const val REQUEST_CODE_SPEND_EDIT = 200

        // Result Code (onActivityResult)
        const val RESULT_OK = 1
        const val RESULT_CANCEL = 0
        const val RESULT_ERROR = -1


        // SEGMENT
        const val SEGMENT_ROOM = "room"
        const val KEY_ROOM_CODE = "code"
    }
}
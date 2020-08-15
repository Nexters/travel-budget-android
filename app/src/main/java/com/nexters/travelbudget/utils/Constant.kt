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
        const val EXTRA_PLAN_ID = "EXTRA_PLAN_ID"


        // SharedPreferences
        const val PREF_NAME = "TRAVEL_BUDGET_PREF"
        const val PREF_USER_TOKEN = "PREF_USER_TOKEN"

        // Request code
        const val REQUEST_CODE_CREATE_ROOM = 20
        const val REQUEST_CODE_ENTER_ROOM = 30
        const val REQUEST_CODE_EDIT_USER_NAME = 40

        // Result Code (onActivityResult)
        const val RESULT_OK = 1
        const val RESULT_CANCEL = 0
        const val RESULT_ERROR = -1
    }
}
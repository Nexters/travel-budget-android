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

        // SharedPreferences
        const val PREF_NAME = "TRAVEL_BUDGET_PREF"

        // Result Code (onActivityResult)
        const val RESULT_OK = 1
        const val RESULT_CANCEL = 0
        const val RESULT_ERROR = -1
    }
}
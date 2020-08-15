package com.nexters.travelbudget.model.enums

/**
 * 여행 방 내에 멤버 권한 타입 (관리자/멤버)
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.16
 */
enum class TripMemberType(val text: String) {
    OWNER("관리자"),
    MEMBER("멤버")
}
package com.nexters.travelbudget.model.enums

/**
 * 여행 방 타입 (공동/개인)
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.11
 */
enum class TravelRoomType(val text: String) {
    PERSONAL("개인 여행"),
    SHARED("공동 여행")
}
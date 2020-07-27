package com.nexters.travelbudget.utils.widget.piechart

data class PieData(
    val tag: String,
    val value: Int,
    val color: Int
) : Comparable<PieData> {
    override fun compareTo(other: PieData): Int {
        return other.value - value
    }
}
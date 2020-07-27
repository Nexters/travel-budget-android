package com.nexters.travelbudget.utils.widget.piechart

data class PieData(
    val tag: String,
    val value: Float,
    val color: Int
) : Comparable<PieData> {
    override fun compareTo(other: PieData): Int {
        val gap = other.value - value
        return when {
            gap > 0 -> 1
            gap < 0 -> -1
            else -> {
                0
            }
        }
    }
}
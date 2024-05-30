package com.beapps.thedoctorapp.core.domain


data class GraphPoint<X, Y>(
    val x: X,
    val y: Y
)

interface GraphManager<X, Y> {
    fun drawGraph(
        points: List<GraphPoint<X, Y>>,
        yLabelCounts: Int,
        descriptionText: String,
        lineLabel: String,
    ): ByteArray
}
package com.beapps.thedoctorapp.core.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.io.ByteArrayOutputStream

class GraphManagerMPChartImpl(
    val context: Context
) : GraphManager<String, Float> {

    private val graphWidth = 2400
    private val graphHeight = 1200
    private val lineColor = android.graphics.Color.BLUE
    private val circleColor = android.graphics.Color.BLUE
    override fun drawGraph(
        points: List<GraphPoint<String, Float>>,
        yLabelCounts: Int,
        descriptionText: String,
        lineLabel: String,
    ): ByteArray {

        val lineChart = LineChart(context)
        val entries = points.mapIndexed { index, graphPoint ->
            Entry(index.toFloat() + 1f , graphPoint.y)
        }
        val dataSet = LineDataSet(entries, lineLabel).apply {
            color = lineColor
            setCircleColor(circleColor)
        }
        val maxY = points.maxOf { it.y }
        val minY = points.minOf { it.y }

        val xLabels = mutableListOf("")
        xLabels.addAll(points.map { it.x })

        lineChart.data = LineData(dataSet)
        lineChart.axisRight.setDrawLabels(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.labelCount = points.size + 1
        lineChart.xAxis.axisMinimum = 0f
        lineChart.xAxis.axisMaximum = points.size.toFloat() + 1
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xLabels)
        lineChart.axisLeft.labelCount = yLabelCounts
        var yAxisBuffer = (maxY - minY) * 0.1f
        if (yAxisBuffer <= 0f) yAxisBuffer = 5f
        lineChart.axisLeft.axisMinimum = minY - yAxisBuffer
        lineChart.axisLeft.axisMaximum = maxY + yAxisBuffer
        val description = Description()
        description.text = descriptionText
        lineChart.description = description
        // Render chart to bitmap
        val bitmap = Bitmap.createBitmap(graphWidth, graphHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        lineChart.layout(0, 0, graphWidth, graphHeight)
        lineChart.draw(canvas)
        // Convert bitmap to byte array
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
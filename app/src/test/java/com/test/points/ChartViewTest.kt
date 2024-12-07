package com.test.points

import com.test.points.business.models.Point
import com.test.points.ui.fragments.points.PointsViewModel.Companion.checkValidCountText
import com.test.points.ui.fragments.points.PointsViewModel.Companion.sortPointsByX
import org.junit.Test

class ChartViewTest {
    private val points: List<Point> =
        listOf(
            Point(-5f, 12f),
            Point(24f, -3f),
            Point(1f, 38f),
            Point(49f, -7f),
            Point(-10f, 6f),
            Point(-5f, 12f)
        )

    @Test
    fun sortPointsTest() {
        val sortedArray = sortPointsByX(points)
        for (i in 0 until sortedArray.size - 1) {
            assert(
                sortedArray[i].x <= sortedArray[i + 1].x,
                "Ошибка сортировки элементов."
            )
        }
    }

    @Test
    fun validCountInputTest() {
        val input = "123"
        assert(
            checkValidCountText(input),
            "Ошибка валидации введенного числа количества точек"
        )
    }

    @Test
    fun invalidCountInputTest() {
        val input = "12s4"
        assert(
            !checkValidCountText(input),
            "Ошибка валидации введенного числа количества точек"
        )
    }

    fun assert(
        value: Boolean,
        failedMessage: String?
    ) {
        if (!value) {
            throw AssertionError(failedMessage)
        }
    }
}

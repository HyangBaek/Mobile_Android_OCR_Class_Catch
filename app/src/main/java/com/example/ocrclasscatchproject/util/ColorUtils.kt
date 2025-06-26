//package com.example.ocrclasscatchproject.util
//
//import kotlin.math.pow
//import kotlin.math.sqrt
//
//object ColorUtils {
//
//    /**
//     * 두 RGB 색상 간 거리(유클리디안 거리)를 계산한다.
//     *
//     * @param c1 첫 번째 색상 (R,G,B) 튜플, 각 값은 0~255 범위의 Int
//     * @param c2 두 번째 색상 (R,G,B) 튜플
//     * @return 색상 간 거리 (Double) - 값이 작을수록 유사함
//     */
//    fun colorDistance(c1: Triple<Int, Int, Int>, c2: Triple<Int, Int, Int>): Double {
//        return sqrt(
//            (c1.first - c2.first).toDouble().pow(2) +
//                    (c1.second - c2.second).toDouble().pow(2) +
//                    (c1.third - c2.third).toDouble().pow(2)
//        )
//    }
//
//    /**
//     * 두 색상이 threshold 값 이내로 유사한지 판단
//     *
//     * @param c1 첫 번째 색상 (R,G,B)
//     * @param c2 두 번째 색상 (R,G,B)
//     * @param threshold 임계값 (기본 50.0) 이하일 때 유사하다고 판단
//     * @return Boolean, 유사하면 true
//     */
//    fun isSimilarColor(c1: Triple<Int, Int, Int>, c2: Triple<Int, Int, Int>, threshold: Double = 50.0): Boolean {
//        return colorDistance(c1, c2) < threshold
//    }
//
//    /**
//     * Android Color Int 값을 RGB Triple로 변환
//     *
//     * @param color ARGB 형식의 Int 색상 값 (예: 0xFF112233)
//     * @return Triple<Int, Int, Int> (R,G,B) 각 0~255 범위
//     */
//    fun colorToTriple(color: Int): Triple<Int, Int, Int> {
//        val r = (color shr 16) and 0xff
//        val g = (color shr 8) and 0xff
//        val b = color and 0xff
//        return Triple(r, g, b)
//    }
//}

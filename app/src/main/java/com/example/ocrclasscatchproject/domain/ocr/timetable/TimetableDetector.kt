//package com.example.ocrclasscatchproject.domain.ocr.timetable
//
//import android.graphics.Bitmap
//import kotlin.math.pow
//import kotlin.math.sqrt
//
///**
// * 시간표 이미지에서 셀 단위로 색상 정보를 분석하여
// * 요일(day), 시간(hour), 색상(color) 정보를 감지하는 클래스
// *
// * @param bitmap 시간표 이미지 비트맵
// * @param cols 열 개수 (요일 수, 기본 7: 월~일)
// * @param rows 행 개수 (시간 슬롯 수, 기본 8: 9시~16시)
// * @param startHour 시작 시간 (기본 9시)
// */
//class TimetableDetector(
//    private val bitmap: Bitmap,
//    private val cols: Int = 7, // 월~일 지원
//    private val rows: Int = 8, // 9시~16시 (8시간)
//    val startHour: Int = 9
//) {
//    // 셀 하나 너비: 전체 너비를 (열수 + 1)로 나눈 값 (왼쪽 시간표 번호 영역 고려)
//    val cellWidth = bitmap.width / (cols + 1)
//    // 셀 하나 높이: 전체 높이를 (행수 + 1)로 나눈 값 (위쪽 요일 헤더 영역 고려)
//    val cellHeight = bitmap.height / (rows + 1)
//
//    // 배경색으로 추정하는 색 (라이트 모드 흰색, 다크 모드 검정색)
//    private val backgroundColors = listOf(
//        Triple(255, 255, 255), // 흰색
//        Triple(0, 0, 0)        // 검정색
//    )
//    // 배경색과 비교 시 색상 차이 임계값
//    private val colorThreshold = 50.0
//
//    // 요일 리스트 (1열부터 월~일)
//    private val days = listOf("월", "화", "수", "목", "금", "토", "일")
//
//    /**
//     * 감지된 셀 정보를 담는 데이터 클래스
//     *
//     * @param day 요일 (월~일)
//     * @param hour 시간 (9~16시 등)
//     * @param color 셀 색상 (ARGB Int)
//     */
//    data class DetectedCell(
//        val day: String,
//        val hour: Int,
//        val color: Int
//    )
//
//    /**
//     * 이미지에서 각 셀의 중앙 픽셀 색상을 검사하여
//     * 배경색과 충분히 다르면 셀로 간주하여 감지 결과를 리스트로 반환
//     *
//     * @return 감지된 셀들의 리스트
//     */
//    fun detectColoredCells(): List<DetectedCell> {
//        val results = mutableListOf<DetectedCell>()
//
//        // 1열부터 cols까지 (요일 영역 제외)
//        for (col in 1..cols) {
//            val day = days.getOrNull(col - 1) ?: continue
//
//            // 1행부터 rows까지 (요일 헤더 영역 제외)
//            for (row in 1..rows) {
//                // 각 셀의 중앙 좌표 계산
//                val x = col * cellWidth + cellWidth / 2
//                val y = row * cellHeight + cellHeight / 2
//
//                // 중앙 픽셀 색상 추출
//                val pixel = bitmap.getPixel(x, y)
//
//                // RGB 분리
//                val r = (pixel shr 16) and 0xff
//                val g = (pixel shr 8) and 0xff
//                val b = pixel and 0xff
//
//                val colorTriple = Triple(r, g, b)
//
//                // 배경색 리스트와 색상 거리 비교 후
//                // 모두 threshold 이상 차이나면 (즉 배경색이 아니면) 셀로 판단
//                if (backgroundColors.all { colorDistance(colorTriple, it) > colorThreshold }) {
//                    results.add(
//                        DetectedCell(
//                            day = day,
//                            hour = startHour + row - 1,
//                            color = pixel
//                        )
//                    )
//                }
//            }
//        }
//        return results
//    }
//
//    private fun colorDistance(c1: Triple<Int, Int, Int>, c2: Triple<Int, Int, Int>): Double {
//        return sqrt(
//            (c1.first - c2.first).toDouble().pow(2) +
//                    (c1.second - c2.second).toDouble().pow(2) +
//                    (c1.third - c2.third).toDouble().pow(2)
//        )
//    }
//
//    fun isSimilarColor(c1: Int, c2: Int, threshold: Double = 50.0): Boolean {
//        val r1 = (c1 shr 16) and 0xff
//        val g1 = (c1 shr 8) and 0xff
//        val b1 = c1 and 0xff
//
//        val r2 = (c2 shr 16) and 0xff
//        val g2 = (c2 shr 8) and 0xff
//        val b2 = c2 and 0xff
//
//        return sqrt(
//            (r1 - r2).toDouble().pow(2) +
//                    (g1 - g2).toDouble().pow(2) +
//                    (b1 - b2).toDouble().pow(2)
//        ) < threshold
//    }
//
//}

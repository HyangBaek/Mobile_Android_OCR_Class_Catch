//package com.example.ocrclasscatchproject.domain.ocr.parser
//
//import android.graphics.Rect
//import com.example.ocrclasscatchproject.util.RegexUtils
//import com.google.mlkit.vision.text.Text
//
//// OCR된 결과를 시간표 항목으로 매핑한 데이터 클래스
//data class TimetableItem(
//    val day: String,       // 요일 (월, 화, ...)
//    val hour: Int,         // 시작 시각 (정수형: 9, 10, 11 등)
//    val subject: String,   // 과목명
//    val room: String       // 강의실
//)
//
///**
// * OCR 결과로부터 텍스트 블록의 위치 정보와 텍스트를 바탕으로
// * 시간표 항목을 추출하는 객체
// */
//object OcrLayoutParser {
//
//    // X좌표 범위를 기반으로 요일 판단
//    private val dayColumnMap = mapOf(
//        "월" to 0..150,
//        "화" to 151..300,
//        "수" to 301..450,
//        "목" to 451..600,
//        "금" to 601..800
//        // 필요 시 토/일 추가 가능
//    )
//
//    // Y좌표 범위를 기반으로 시간대 판단 (예: 9시~13시)
//    private val timeRowMap = mapOf(
//        9 to 0..200,
//        10 to 201..400,
//        11 to 401..600,
//        12 to 601..800,
//        13 to 801..1000
//        // 필요 시 14시 이후 추가 가능
//    )
//
//    // X좌표로 요일 추출
//    private fun getDayFromX(x: Int): String? {
//        return dayColumnMap.entries.firstOrNull { x in it.value }?.key
//    }
//
//    // Y좌표로 시간 추출
//    private fun getHourFromY(y: Int): Int? {
//        return timeRowMap.entries.firstOrNull { y in it.value }?.key
//    }
//
//    /**
//     * OCR 블록 리스트에서 강의 정보 추출
//     * - subject → 한글로 된 과목명 (Regex로 필터)
//     * - room → 강의실 번호 (ex: 601-1234)
//     * - 블록의 중앙 좌표로부터 day/hour 추론
//     *
//     * @param blocks ML Kit이 반환한 OCR 블록 리스트
//     * @return 추출된 시간표 항목 리스트
//     */
//    fun parseBlocks(blocks: List<Text.TextBlock>): List<TimetableItem> {
//        val result = mutableListOf<TimetableItem>()
//
//        // 상태를 일시적으로 저장할 변수
//        var currentSubject: String? = null
//        var currentRoom: String? = null
//        var currentRect: Rect? = null
//
//        for (block in blocks) {
//            val text = block.text
//            val rect = block.boundingBox ?: continue
//
//            // 과목명인지 판단: 한글로만 이루어진 문자열
//            if (text.matches(RegexUtils.KOREAN_TEXT)) {
//                currentSubject = text
//                currentRect = rect
//            }
//
//            // 강의실명인지 판단: 3자리-4자리 숫자 형태
//            if (text.matches(RegexUtils.ROOM_REGEX)) {
//                currentRoom = text
//            }
//
//            // subject + room + 위치 정보가 모두 있을 때 → 하나의 시간표 항목으로 처리
//            if (currentSubject != null && currentRoom != null && currentRect != null) {
//                val day = getDayFromX(currentRect.centerX()) ?: continue
//                val hour = getHourFromY(currentRect.centerY()) ?: continue
//
//                result.add(
//                    TimetableItem(
//                        day = day,
//                        hour = hour,
//                        subject = currentSubject,
//                        room = currentRoom
//                    )
//                )
//
//                // 다음 블록을 위해 초기화
//                currentSubject = null
//                currentRoom = null
//                currentRect = null
//            }
//        }
//
//        return result
//    }
//}
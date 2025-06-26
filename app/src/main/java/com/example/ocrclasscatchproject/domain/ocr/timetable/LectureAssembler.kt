//package com.example.ocrclasscatchproject.domain.ocr.timetable
//
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.util.ColorUtils.colorToTriple
//import com.example.ocrclasscatchproject.util.ColorUtils.isSimilarColor
//import com.google.mlkit.vision.text.Text
//import android.util.Log
//
///**
// * OCR 텍스트 블록과 시간표 셀 매핑 결과를 바탕으로 Lecture 리스트를 생성하는 객체
// */
//object LectureAssembler {
//
//    /**
//     * OCR에서 추출된 텍스트 블록과 셀 매핑 정보를 받아서 강의 리스트로 변환
//     *
//     * @param blocks OCR 텍스트 블록 리스트
//     * @param cellMap 각 텍스트 블록이 속한 시간표 셀 매핑 (TextBlock -> DetectedCell)
//     * @return Lecture 리스트
//     */
//    fun assembleLectures(
//        blocks: List<Text.TextBlock>,
//        cellMap: Map<Text.TextBlock, TimetableDetector.DetectedCell>
//    ): List<Lecture> {
//        // 셀별로 텍스트를 그룹핑 (DetectedCell -> 텍스트 리스트)
//        val grouped = mutableMapOf<TimetableDetector.DetectedCell, MutableList<String>>()
//
//        for (block in blocks) {
//            val cell = cellMap[block] ?: continue
//            grouped.getOrPut(cell) { mutableListOf() }.add(block.text)
//        }
//
//        return grouped.map { (cell, texts) ->
//            // 텍스트 중 강의실 정보("601-xxxx")는 제외하고 과목명 후보군으로 사용
//            val subjectCandidates = texts.filterNot { it.contains("601-") }
//            // 여러 텍스트를 공백으로 합치고 줄바꿈 제거, 비었으면 "강의명 없음"으로 대체
//            val subject = subjectCandidates.joinToString(" ").replace("\n", " ").takeIf { it.isNotBlank() }
//                ?: "강의명 없음"
//
//            // 강의실 정보는 "601-" 포함하는 텍스트에서 찾아내고 없으면 "강의실 없음"
//            val room = texts.find { it.contains("601-") } ?: "강의실 없음"
//
//            Log.d("LectureAssembler", "assembled lectures: ${subject}, ${cell.day}, ${room}, ${cell.hour + 1}")
//
//            // Lecture 객체 생성, 수업 시간은 기본적으로 1시간으로 설정
//            Lecture(
//                day = listOf(cell.day),
//                startHour = "${cell.hour}",
//                endHour = "${cell.hour + 1}", // 기본 1시간 수업으로 가정 (후에 병합처리 가능)
//                subject = subject,
//                room = room,
//                // 강의명 또는 강의실 정보가 없으면 자동 감지 플래그를 true로 설정
//                isAutoDetected = subject == "강의명 없음" || room == "강의실 없음"
//            )
//        }
//    }
//
//    /**
//     * 색상 정보를 기반으로 인접 셀을 병합하여 Lecture 리스트 생성 (자동 감지 전용)
//     *
//     * @param cells 시간표의 감지된 셀 리스트
//     * @return Lecture 리스트 (과목명/강의실 정보는 없음)
//     */
//    fun assembleLecturesFromColors(cells: List<TimetableDetector.DetectedCell>): List<Lecture> {
//        val groupedLectures = mutableListOf<Lecture>()
//
//        // 요일별로 셀 그룹화
//        val groupedByDay = cells.groupBy { it.day }
//
//        for ((day, dayCells) in groupedByDay) {
//            val sorted = dayCells.sortedBy { it.hour }
//
//            // 강의 시간 병합을 위한 변수 초기화
//            var start = sorted.first().hour
//            var prev = start
//            var color = sorted.first().color
//
//            // 인접 시간대면서 색상이 유사한 셀은 하나의 강의로 병합
//            for (i in 1 until sorted.size) {
//                val current = sorted[i]
//                if (isSimilarColor(colorToTriple(current.color), colorToTriple(color)) && current.hour == prev + 1) {
//                    prev = current.hour
//                } else {
//                    // 병합 완료된 구간을 Lecture로 추가
//                    groupedLectures.add(
//                        Lecture(
//                            day = listOf(day),
//                            startHour = "$start",
//                            endHour = "${prev + 1}",
//                            subject = "강의명 없음",
//                            room = "강의실 없음",
//                            isAutoDetected = true
//                        )
//                    )
//                    // 새 구간 시작
//                    start = current.hour
//                    prev = current.hour
//                    color = current.color
//                }
//            }
//
//            // 마지막 병합 구간 추가
//            groupedLectures.add(
//                Lecture(
//                    day = listOf(day),
//                    startHour = "$start",
//                    endHour = "${prev + 1}",
//                    subject = "강의명 없음",
//                    room = "강의실 없음",
//                    isAutoDetected = true
//                )
//            )
//        }
//
//        return groupedLectures
//    }
//}
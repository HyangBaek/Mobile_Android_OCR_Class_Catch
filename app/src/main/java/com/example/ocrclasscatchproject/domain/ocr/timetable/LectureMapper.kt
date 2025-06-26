//package com.example.ocrclasscatchproject.domain.ocr.timetable
//
//import android.graphics.Rect
//import com.example.ocrclasscatchproject.domain.ocr.timetable.TimetableDetector.DetectedCell
//
///**
// * OCR에서 감지된 boundingBox(텍스트 블록의 사각형 영역)의 중심 좌표가
// * 시간표 셀 내에 포함되는지를 판단하여, 적절한 셀(요일/시간)을 찾아주는 매핑 유틸리티
// */
//object LectureMapper {
//
//    /**
//     * OCR 결과의 텍스트 블록이 어느 시간표 셀에 속하는지 판단
//     *
//     * @param box ML Kit의 OCR 텍스트 블록의 boundingBox
//     * @param detector 셀 좌표 계산 및 색상 정보 등을 가진 TimetableDetector
//     * @return 해당 셀에 해당하는 DetectedCell 객체, 없으면 null
//     */
//    fun matchBoundingBoxToCell(
//        box: Rect,
//        detector: TimetableDetector
//    ): DetectedCell? {
//        // 감지된 셀 목록 불러오기 (색상 기준으로 감지된 셀)
//        val cells = detector.detectColoredCells()
//
//        // 텍스트 블록의 중심 좌표 계산
//        val centerX = box.exactCenterX().toInt()
//        val centerY = box.exactCenterY().toInt()
//
//        // 각 셀의 위치를 계산하고, 중심 좌표가 포함되는 셀을 찾는다
//        return cells.find { cell ->
//            val x = detector.cellX(cell.day)
//            val y = detector.cellY(cell.hour)
//            val w = detector.cellWidth
//            val h = detector.cellHeight
//
//            // 셀 사각형 내에 중심점이 포함되는지 확인
//            Rect(x, y, x + w, y + h).contains(centerX, centerY)
//        }
//    }
//}
//
///**
// * 셀의 x좌표(요일 기준)를 계산하는 확장 함수
// *
// * @param day 요일 문자열 ("월", "화", ...)
// * @return 해당 요일에 해당하는 셀의 x 시작 위치
// */
//fun TimetableDetector.cellX(day: String): Int {
//    val index = listOf("월", "화", "수", "목", "금").indexOf(day)
//    return (index + 1) * this.cellWidth
//}
//
///**
// * 셀의 y좌표(시간 기준)를 계산하는 확장 함수
// *
// * @param hour 해당 셀의 시간 (ex: 9시)
// * @return 해당 시간에 해당하는 셀의 y 시작 위치
// */
//fun TimetableDetector.cellY(hour: Int): Int {
//    return (hour - this.startHour + 1) * this.cellHeight
//}

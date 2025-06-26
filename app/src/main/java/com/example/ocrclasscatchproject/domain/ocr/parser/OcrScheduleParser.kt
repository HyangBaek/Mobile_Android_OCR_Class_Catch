//package com.example.ocrclasscatchproject.domain.ocr.parser
//
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//
///**
// * OCR에서 추출된 텍스트 라인들을 기반으로 간단한 규칙을 적용해 강의 정보를 파싱하는 유틸 객체
// */
//object OcrScheduleParser {
//
//    // 요일 키워드 목록 (단순 매칭 기반)
//    private val dayKeywords = listOf("월", "화", "수", "목", "금")
//
//    /**
//     * 텍스트 줄 리스트를 받아 Lecture 리스트로 변환
//     * 현재는 시간 정보 없이 요일/과목/강의실 정보만 구성
//     */
//    fun parse(lines: List<String>): List<Lecture> {
//        val results = mutableListOf<Lecture>()
//        var currentDay: String? = null       // 현재 라인에서 감지된 요일
//        val startHour: String? = null
//        val endHour: String? = null
//        var tempSubject: String? = null      // 현재 추정 과목명
//
//        for (line in lines) {
//            when {
//                // 요일이 감지되면 저장
//                dayKeywords.contains(line) -> {
//                    currentDay = line
//                }
//
//                // 강의실 패턴(예: 101-204) 감지 시 Lecture 객체 생성
//                line.matches(Regex("\\d{3}-\\d{3,4}")) -> {
//                    if (currentDay != null && tempSubject != null) {
//                        results.add(
//                            Lecture(
//                                day = listOf(currentDay),
//                                startHour = "11",        // 추정 또는 향후 개선 예정
//                                endHour = "12",
//                                subject = tempSubject,
//                                room = line,
//                                isAutoDetected = true
//                            )
//                        )
//                        tempSubject = null // 과목명 초기화
//                    }
//                }
//
//                // 과목명 후보 (길이 기반 단순 필터링)
//                line.length in 3..20 -> {
//                    tempSubject = line
//                }
//            }
//        }
//
//        return results
//    }
//}

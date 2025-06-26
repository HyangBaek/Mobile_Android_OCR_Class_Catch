//package com.example.ocrclasscatchproject.domain.ocr.usecase
//
//import android.util.Log
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//
///**
// * OCR로 추출한 텍스트에서 강의 정보를 파싱하는 UseCase 클래스
// */
//class ParseOcrTextUseCase {
//
//    // 강의 요일로 인식할 키워드 리스트
//    private val dayKeywords = listOf("월", "화", "수", "목", "금", "토", "일")
//
//    /**
//     * OCR 결과 텍스트를 받아 Lecture 리스트로 변환
//     */
//    operator fun invoke(rawText: String): List<Lecture> {
//        // 줄 단위로 분할 → 공백 제거 → 빈 줄 제거
//        val lines = rawText.split("\n")
//            .map { it.trim() }
//            .filter { it.isNotEmpty() }
//
//        val lectures = mutableListOf<Lecture>()       // 결과로 반환할 강의 리스트
//        var currentDay: String? = null                // 현재 처리 중인 요일
//        var buffer = mutableListOf<String>()          // 강의 정보 임시 저장 버퍼
//
//        for (line in lines) {
//            // 라인이 요일인 경우 → 요일 설정 및 버퍼 초기화
//            if (dayKeywords.contains(line)) {
//                currentDay = line
//                buffer.clear()
//                continue
//            }
//
//            // 강의실로 보이는 패턴 인식 (예: 601-0401)
//            val isRoom = Regex("""\d{3}-\d{4}""").matches(line)
//
//            // 현재 라인을 버퍼에 추가
//            buffer.add(line)
//
//            // 강의실이 탐지되었고, 요일이 설정된 경우 → 강의 정보 완성
//            if (isRoom && currentDay != null) {
//                // 버퍼 구조 예시: [과목명1, 과목명2?, ..., 강의실]
//                val room = buffer.last()                     // 마지막 줄 = 강의실
//                val subjectParts = buffer.dropLast(1)        // 나머지 줄 = 과목명 파츠
//                val subject = subjectParts.joinToString(" ") // 공백으로 연결해 과목명 구성
//
//                // 시간 정보는 현재 하드코딩 (11:00~12:00)
//                lectures.add(
//                    Lecture(
//                        day = listOf(currentDay),
//                        startHour = "11", // TODO: 실제 시간 추출 로직 필요
//                        endHour = "12",
//                        subject = subject,
//                        room = room
//                    )
//                )
//
//                // 다음 강의를 위해 버퍼 초기화
//                buffer.clear()
//            }
//
//            // 로그로 디버깅 정보 출력
//            Log.d("OcrDebug", "lectures.size = ${lectures.size}")
//            lectures.forEach {
//                Log.d("OcrDebug", it.toString())
//            }
//        }
//
//        return lectures
//    }
//}
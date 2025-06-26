//package com.example.ocrclasscatchproject.util
//
///**
// * OCR로 추출된 원시 텍스트를 후처리하여
// * 의미있는 라인들만 추출하고 불필요한 공백을 제거하는 유틸 객체
// */
//object TextPostProcessor {
//
//    /**
//     * 원시 텍스트에서 라인별로 처리하여 다음 작업 수행
//     * - 앞뒤 공백 제거
//     * - 빈 줄 제거
//     * - 숫자만 있는 줄 제거
//     * - 한글 단어 사이 불필요한 공백 제거
//     *
//     * @param rawText OCR로 추출된 원시 텍스트
//     * @return 후처리된 의미있는 텍스트 라인 리스트
//     */
//    fun cleanLines(rawText: String): List<String> {
//        return rawText
//            .lines()
//            .map { it.trim() }                       // 앞뒤 공백 제거
//            .filter { it.isNotBlank() }              // 빈 줄 제거
//            .filterNot { it.matches(Regex("^\\d+\$")) } // 숫자만 있는 줄 제거
//            .map { fixSpacedWords(it) }              // 한글 단어 사이 공백 제거
//    }
//
//    /**
//     * 한글 단어 사이에 들어간 공백을 제거함으로써
//     * 잘못 분리된 단어를 붙여줌
//     *
//     * 예) "백 에 드 프로 그 래" → "백엔드프로그래"
//     *
//     * @param text 공백 포함 한글 텍스트
//     * @return 공백이 제거된 텍스트
//     */
//    private fun fixSpacedWords(text: String): String {
//        return text
//            .replace("  ", " ")                      // 연속된 공백 2개 이상 → 1개로 축소
//            .replace(Regex("([가-힣]) ([가-힣])"), "$1$2")  // 한글 글자 사이의 단일 공백 제거
//    }
//}
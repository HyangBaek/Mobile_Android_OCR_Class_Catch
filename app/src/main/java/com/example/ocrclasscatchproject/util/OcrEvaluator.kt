//package com.example.ocrclasscatchproject.util
//
///**
// * OCR 결과 텍스트의 의미있음을 평가하는 유틸리티 객체
// *
// * 핵심 키워드 포함 여부와 한글 글자 수를 기반으로 점수를 산정하여
// * 두 텍스트 중 어느 쪽이 더 의미있는지 판단한다.
// */
//object OcrEvaluator {
//
//    // 평가에 활용할 핵심 키워드 리스트
//    private val keyKeywords = listOf("학기", "시간표", "학점", "이름", "검색", "월", "화", "수", "목", "금")
//
//    /**
//     * 두 텍스트 중 더 의미있다고 판단되는지 비교
//     *
//     * @param text1 첫 번째 텍스트
//     * @param text2 두 번째 텍스트
//     * @return text1이 더 의미있거나 같으면 true, 그렇지 않으면 false
//     */
//    fun isMoreMeaningful(text1: String, text2: String): Boolean {
//        val score1 = scoreText(text1)
//        val score2 = scoreText(text2)
//        return score1 >= score2
//    }
//
//    /**
//     * 텍스트 점수 산정 함수
//     *
//     * 점수 산정 기준:
//     * - 한글 글자 수만큼 점수 부여
//     * - 핵심 키워드 포함 시 추가 가중치 20점 부여
//     *
//     * @param text 평가할 텍스트
//     * @return 점수 (Int)
//     */
//    private fun scoreText(text: String): Int {
//        var score = 0
//        // 한글 글자 수 계산
//        score += text.count { it in '가'..'힣' }
//        // 핵심 키워드 포함 시 가중치 부여
//        if (containsKeyword(text)) score += 20
//        return score
//    }
//
//    /**
//     * 텍스트에 핵심 키워드가 포함되어 있는지 검사
//     *
//     * @param text 검사할 텍스트
//     * @return 포함되어 있으면 true, 아니면 false
//     */
//    private fun containsKeyword(text: String): Boolean {
//        return keyKeywords.any { it in text }
//    }
//}

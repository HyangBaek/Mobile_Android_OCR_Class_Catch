//package com.example.ocrclasscatchproject.util
//
///**
// * 정규식(Regex) 관련 상수를 모아둔 유틸 객체
// */
//object RegexUtils {
//
//    /**
//     * 강의실 번호 패턴: "601-" 다음에 4자리 숫자가 오는 형태
//     * 예) 601-0401, 601-1234
//     */
//    val ROOM_REGEX = Regex("601-\\d{4}")
//
//    /**
//     * 한글 텍스트 존재 여부 검사 정규식
//     * 최소 2글자 이상의 연속된 한글 포함 여부를 확인
//     */
//    val KOREAN_TEXT = Regex(".*[가-힣]{2,}.*")
//}
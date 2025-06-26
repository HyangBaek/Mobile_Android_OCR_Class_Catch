//package com.example.ocrclasscatchproject.domain.ocr.model
//
//import android.os.Parcelable
//import kotlinx.parcelize.Parcelize
//import java.util.UUID
//
///**
// * OCR 결과로부터 추출된 개별 강의 정보를 담는 데이터 클래스
// */
//@Parcelize
//data class Lecture(
//    val id: String = UUID.randomUUID().toString(), // 각 강의 항목의 고유 ID (자동 생성)
//
//    val day: List<String>?, // 강의 요일 (예: ["월", "수"] 등 복수 요일 가능)
//
//    val startHour: String,  // 강의 시작 시간 (예: "09:00")
//    val endHour: String,    // 강의 종료 시간 (예: "10:30")
//
//    val subject: String,    // 강의명 (예: "자료구조", "컴퓨터네트워크")
//    val room: String,       // 강의실 정보 (예: "IT관 305호")
//
//    val isAutoDetected: Boolean = false // OCR로 자동 추출된 항목인지 여부 (수동 입력과 구분용)
//) : Parcelable // Android의 Fragment 간 전달을 위해 Parcelable 구현

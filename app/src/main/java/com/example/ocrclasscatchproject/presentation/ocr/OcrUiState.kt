//package com.example.ocrclasscatchproject.presentation.ocr
//
//import android.graphics.Bitmap
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//
///**
// * OCR 프래그먼트의 화면 상태를 표현하는 UI 상태 데이터 클래스
// */
//data class OcrUiState(
//    // OCR 결과 텍스트 (ML Kit 등으로 인식된 전체 텍스트)
//    val resultText: String = "",
//
//    // OCR 처리 후 표시할 이미지 (선택한 이미지의 Bitmap)
//    val bitmap: Bitmap? = null,
//
//    // OCR 결과로부터 파싱된 강의 리스트
//    val scheduledLectures: List<Lecture> = emptyList(),
//    val isLoading: Boolean = false,
//)

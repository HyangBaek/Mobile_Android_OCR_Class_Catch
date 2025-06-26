//package com.example.ocrclasscatchproject.domain.ocr.model
//
//import android.graphics.Bitmap
//
///**
// * OCR 처리 결과를 저장하는 데이터 클래스
// *
// * @property text     OCR로 인식한 전체 텍스트 (후처리된 문자열)
// * @property bitmap   OCR 대상이 되었던 원본 또는 전처리된 이미지
// * @property lectures OCR 결과로부터 추출된 강의 리스트
// */
//data class OcrResult(
//    val text: String,                          // OCR 인식 후 최종적으로 정리된 텍스트
//    val bitmap: Bitmap,                        // OCR 대상이 된 이미지 (표시용으로 활용)
//    val lectures: List<Lecture> = emptyList()  // OCR 결과로 파싱된 강의 정보 리스트
//)
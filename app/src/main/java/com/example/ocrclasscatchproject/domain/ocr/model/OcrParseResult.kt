//package com.example.ocrclasscatchproject.domain.ocr.model
//
//import com.google.mlkit.vision.text.Text
//
///**
// * OCR 처리 결과를 저장하는 데이터 클래스
// *
// * @property fullText 전체 인식된 텍스트 (한 문자열로 통합된 결과)
// * @property textBlocks ML Kit이 반환한 텍스트 블록 리스트 (위치 정보 포함)
// */
//data class OcrParseResult(
//    val fullText: String,                      // OCR로 인식된 전체 텍스트
//    val textBlocks: List<Text.TextBlock>       // 각 블록 단위의 텍스트 및 위치 정보
//)
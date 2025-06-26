//package com.example.ocrclasscatchproject.domain.ocr.usecase
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.util.Log
//import com.example.ocrclasscatchproject.domain.ocr.model.OcrParseResult
//import com.example.ocrclasscatchproject.domain.ocr.model.ParsedSchedule
//import com.example.ocrclasscatchproject.domain.ocr.model.OcrResult
//import com.example.ocrclasscatchproject.domain.ocr.parser.OcrLayoutParser
//import com.example.ocrclasscatchproject.domain.ocr.parser.OcrScheduleParser
//import com.example.ocrclasscatchproject.domain.ocr.timetable.LectureAssembler
//import com.example.ocrclasscatchproject.domain.ocr.timetable.LectureMapper
//import com.example.ocrclasscatchproject.domain.ocr.timetable.TimetableDetector
//import com.example.ocrclasscatchproject.util.ImagePreprocessor
//import com.example.ocrclasscatchproject.util.TextPostProcessor
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.text.TextRecognition
//import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.tasks.await
//import java.io.IOException
//
///**
// * 이미지 URI를 받아 OCR 처리 및 시간표 파싱을 수행하는 UseCase
// */
//class ProcessImageUseCase {
//
//    // suspend 함수로 코루틴 내에서 호출 가능
//    suspend operator fun invoke(context: Context, uri: Uri): OcrResult = coroutineScope {
//        // 1. 이미지 비트맵 디코딩
//        val bitmap = decodeBitmap(context, uri)
//
//        // 2. 이미지 전처리 (해상도 확대 → 선명도 향상 → 대비 강화 → 색상 반전)
//        val preprocessed = ImagePreprocessor.invert(
//            ImagePreprocessor.sharpen(
//                ImagePreprocessor.enhanceContrast(
//                    ImagePreprocessor.upscale(bitmap)
//                )
//            )
//        )
//
//        // 3. ML Kit OCR 실행 (전처리된 이미지 사용)
//        val mlkitResult = runMlkitOCR(preprocessed)
//
//        // 4. OCR 텍스트 블록을 시간표 셀과 매칭하기 위한 검출기 생성
//        val detector = TimetableDetector(bitmap)
//
//        // 5. 각 텍스트 블록의 바운딩 박스를 셀과 매칭 (맵으로 저장)
//        val cellMap = mlkitResult.textBlocks.mapNotNull { block ->
//            block.boundingBox?.let { box ->
//                LectureMapper.matchBoundingBoxToCell(box, detector)?.let { cell ->
//                    block to cell
//                }
//            }
//        }.toMap()
//
//        // 6. 셀 정보와 텍스트 블록을 바탕으로 강의 정보 조립
//        val lectures = LectureAssembler.assembleLectures(mlkitResult.textBlocks, cellMap)
//
//        // 7. OCR 텍스트 블록을 레이아웃 형태로 파싱하여 텍스트 아이템 리스트 생성
//        val parsedItems = OcrLayoutParser.parseBlocks(mlkitResult.textBlocks)
//
//        // 8. OCR 전체 텍스트에서 시간표 관련 텍스트 추출 및 강의 리스트 파싱
//        val parsedSchedule = processScheduleFromText(mlkitResult.fullText)
//
//        Log.d("ProcessImageUseCase", "returning OcrResult with ${lectures.size} lectures")
//
//        // 9. OCR 결과 객체 반환 (텍스트, 원본 비트맵, 강의 리스트 포함)
//        return@coroutineScope OcrResult(
//            text = parsedItems.joinToString("\n"),
//            bitmap = bitmap,
//            lectures = lectures
//        )
//    }
//
//    /**
//     * 주어진 Uri에서 Bitmap을 디코딩
//     */
//    private fun decodeBitmap(context: Context, uri: Uri): Bitmap {
//        context.contentResolver.openInputStream(uri)?.use { input ->
//            return BitmapFactory.decodeStream(input)
//        } ?: throw IOException("Cannot open input stream")
//    }
//
//    /**
//     * ML Kit Korean OCR을 실행하고 결과를 반환
//     */
//    private suspend fun runMlkitOCR(bitmap: Bitmap): OcrParseResult {
//        val recognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
//        val image = InputImage.fromBitmap(bitmap, 0)
//        val result = recognizer.process(image).await()
//
//        Log.d("runMlkitOCR result", "${result.text}")
//        Log.d("runMlkitOCR result", "${result.textBlocks}")
//
//        return OcrParseResult(
//            fullText = result.text,
//            textBlocks = result.textBlocks
//        )
//    }
//
//    /**
//     * OCR로 인식한 전체 텍스트에서 시간표 라인별로 정리하고,
//     * 강의 정보 리스트를 파싱하여 반환
//     */
//    fun processScheduleFromText(rawText: String): ParsedSchedule {
//        // 불필요한 공백, 줄바꿈 제거 등 텍스트 후처리
//        val lines = TextPostProcessor.cleanLines(rawText)
//
//        // 후처리된 라인에서 강의 리스트 파싱
//        val lectures = OcrScheduleParser.parse(lines)
//
//        lectures.forEach {
//            Log.d("SCHEDULE_ITEM", "${it.day} / ${it.subject} / ${it.room}")
//        }
//
//        return ParsedSchedule(lines, lectures)
//    }
//}

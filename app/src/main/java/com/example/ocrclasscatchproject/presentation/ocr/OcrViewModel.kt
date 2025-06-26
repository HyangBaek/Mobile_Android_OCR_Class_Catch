//package com.example.ocrclasscatchproject.presentation.ocr
//
//import android.graphics.Bitmap
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.domain.ocr.usecase.ParseOcrTextUseCase
//import com.example.ocrclasscatchproject.domain.ocr.usecase.ProcessImageUseCase
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
//// Hilt ViewModel로 등록
//@HiltViewModel
//class OcrViewModel @Inject constructor(
//    // 이미지 처리 (OCR 실행 포함) UseCase
//    private val processImageUseCase: ProcessImageUseCase,
//    // OCR 결과 텍스트 → 강의 리스트로 파싱하는 UseCase
//    private val parseOcrTextUseCase: ParseOcrTextUseCase
//) : ViewModel() {
//
//    // UI 상태를 관리하는 StateFlow (OCR 결과, 로딩 상태 등 포함)
//    private val _uiState = MutableStateFlow(OcrUiState())
//    val uiState: StateFlow<OcrUiState> = _uiState
//
//    // 별도로 파싱된 Lecture 리스트를 저장하는 StateFlow
//    private val _lectures = MutableStateFlow<List<Lecture>>(emptyList())
//    val lectures: StateFlow<List<Lecture>> = _lectures
//
//    /**
//     * 이미지 URI를 받아 OCR 처리를 실행하고, 결과 텍스트와 강의 리스트를 UI 상태에 반영
//     */
//    fun processImage(context: Context, uri: Uri) {
//        viewModelScope.launch {
//            Log.d("OcrTest", "B Lectures: ${_uiState.value.scheduledLectures}")
//
//            // 로딩 상태로 UI 전환
//            _uiState.update { it.copy(isLoading = true) }
//
//            // 백그라운드에서 이미지 처리 (OCR 실행)
//            val result = withContext(Dispatchers.IO) {
//                processImageUseCase(context, uri)
//            }
//
//            Log.d("OcrViewModel processImage B", "result.lectures: ${result.lectures}")
//
//            // OCR 결과 (텍스트, 이미지, 강의 정보 등)를 UI 상태에 반영
//            _uiState.update {
//                it.copy(
//                    resultText = result.text,
//                    bitmap = result.bitmap,
//                    scheduledLectures = result.lectures,
//                    isLoading = false
//                )
//            }
//
//            // OCR 텍스트를 이용한 강의 파싱 (예외 처리나 가공용)
//            onOcrTextReceived(result.text)
//
//            // 강의 리스트를 따로 StateFlow에도 저장 (ScheduleFragment에서 활용 가능)
//            _lectures.value = result.lectures
//
//            Log.d("OcrTest", "A Lectures: ${_uiState.value.scheduledLectures}")
//        }
//    }
//
//    /**
//     * OCR 텍스트를 수동으로 전달받아 강의 파싱을 수행할 경우 호출
//     */
//    fun onOcrTextReceived(text: String) {
//        val lectures = parseOcrTextUseCase(text)
//        _lectures.value = lectures
//    }
//}
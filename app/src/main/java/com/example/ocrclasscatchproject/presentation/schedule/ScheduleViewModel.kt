//package com.example.ocrclasscatchproject.presentation.schedule
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.domain.ocr.parser.OcrScheduleParser
//import com.example.ocrclasscatchproject.util.TextPostProcessor
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import java.util.UUID
//import javax.inject.Inject
//
//@HiltViewModel
//class ScheduleViewModel @Inject constructor() : ViewModel() {
//
//    // 강의 목록 상태 플로우 (읽기 전용 외부 공개)
//    private val _scheduledLectures = MutableStateFlow<List<Lecture>>(emptyList())
//    val scheduledLectures: StateFlow<List<Lecture>> = _scheduledLectures
//
//    // 강의 리스트 전체를 교체
//    fun setSchedule(items: List<Lecture>) {
//        _scheduledLectures.value = items
//    }
//
//    // 강의 하나를 리스트에 추가 (기존 리스트 + 새 강의)
//    fun addLecture(lecture: Lecture) {
//        _scheduledLectures.value = _scheduledLectures.value + lecture
//    }
//
//    // 기존 강의를 id 기준으로 찾아 업데이트 (id가 같으면 교체, 아니면 그대로 유지)
//    fun updateLecture(updatedLecture: Lecture) {
//        _scheduledLectures.value = _scheduledLectures.value.map {
//            if (it.id == updatedLecture.id) updatedLecture else it
//        }
//    }
//
//    /**
//     * OCR로 인식된 원시 텍스트를 받아서
//     * 1) 텍스트 전처리(cleanLines) 수행
//     * 2) OcrScheduleParser를 사용해 강의 리스트 파싱
//     * 3) 파싱 결과를 Lecture 객체 리스트로 변환 (UUID 랜덤 생성)
//     * 4) 변환된 리스트를 상태로 저장
//     *
//     * 비동기 처리 위해 viewModelScope 내에서 launch
//     */
//    fun loadScheduleFromRawText(rawText: String) {
//        viewModelScope.launch {
//            // 텍스트 라인별 전처리: 불필요 공백 제거 등
//            val cleanedLines = TextPostProcessor.cleanLines(rawText)
//
//            // 전처리된 라인을 OcrScheduleParser로 파싱하여 Lecture 형태의 리스트로 변환 (임시 데이터 클래스 변환 포함)
//            val parsed = OcrScheduleParser.parse(cleanedLines)
//
//            // ScheduleItem → Lecture 변환, 고유 id 생성 UUID.randomUUID().toString() 사용
//            val lectures = parsed.map {
//                Lecture(
//                    id = UUID.randomUUID().toString(),
//                    day = it.day,
//                    startHour = it.startHour,
//                    endHour = it.endHour,
//                    subject = it.subject,
//                    room = it.room
//                )
//            }
//
//            // 변환된 강의 리스트를 상태로 설정
//            _scheduledLectures.value = lectures
//        }
//    }
//}
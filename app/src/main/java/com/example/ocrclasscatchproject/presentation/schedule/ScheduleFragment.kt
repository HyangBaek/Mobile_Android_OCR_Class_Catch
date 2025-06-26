//package com.example.ocrclasscatchproject.presentation.schedule
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.ocrclasscatchproject.R
//import com.example.ocrclasscatchproject.databinding.FragmentScheduleBinding
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.presentation.ocr.OcrViewModel
//import com.example.ocrclasscatchproject.presentation.ocr.adapter.LectureAdapter
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//import java.util.UUID
//
//@AndroidEntryPoint
//class ScheduleFragment : Fragment() {
//
//    // OCR 결과와 강의 스케줄 관련 ViewModel 참조
//    private val ocrViewModel: OcrViewModel by activityViewModels()
//    private val scheduleViewModel: ScheduleViewModel by activityViewModels()
//
//    private lateinit var lectureAdapter: LectureAdapter
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var fabAddLecture: FloatingActionButton  // 강의 추가용 플로팅 액션 버튼
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // fragment_schedule 레이아웃 인플레이트
//        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
//
//        // RecyclerView 초기화 및 어댑터 연결
//        recyclerView = view.findViewById(R.id.recyclerSchedule)
//        lectureAdapter = LectureAdapter { lecture ->
//            // 강의 아이템 클릭 시 상세화면으로 이동
//            val detailFragment = LectureDetailFragment.newInstance(lecture)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, detailFragment)
//                .addToBackStack(null)  // 뒤로가기 가능하도록 백스택에 추가
//                .commit()
//        }
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = lectureAdapter
//
//        // 플로팅 액션 버튼 초기화 및 클릭 리스너 설정
//        fabAddLecture = view.findViewById(R.id.fabAddLecture)
//        fabAddLecture.setOnClickListener {
//            // 새 강의 추가: 빈 Lecture 객체 생성 후 상세 편집 화면으로 이동
//            val newLecture = Lecture(
//                UUID.randomUUID().toString(),  // 고유 ID 랜덤 생성
//                emptyList(), "", "", "", ""     // 빈 강의 데이터 초기값
//            )
//            val detailFragment = LectureDetailFragment.newInstance(newLecture)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, detailFragment)
//                .addToBackStack(null)
//                .commit()
//        }
//
//        // OCR ViewModel 상태 구독: OCR 결과 기반 강의 리스트 UI 업데이트
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            ocrViewModel.uiState.collectLatest { state ->
//                val lectures = state.scheduledLectures
//                Log.d("ScheduleFragment", "state.scheduledLectures: $lectures")
//
//                if (lectures.isNotEmpty()) {
//                    // OCR에서 인식된 강의 리스트가 있다면 어댑터에 제출
//                    lectureAdapter.submitList(lectures)
//                } else {
//                    // OCR 결과가 비어있을 경우 테스트용 샘플 데이터를 보여줌
//                    val sampleData = listOf(
//                        Lecture(UUID.randomUUID().toString(), listOf("화"), "11", "12", "테스트용입니다", "601-0401"),
//                        Lecture(UUID.randomUUID().toString(), listOf("수"), "13", "14", "변환이 지원되지 않습니다", "601-0403")
//                    )
//                    Log.d("ScheduleFragment", "sample data: $sampleData")
//                    lectureAdapter.submitList(sampleData)
//                }
//            }
//        }
//
//        // ScheduleViewModel의 강의 리스트 변경 구독 (실제 저장된 강의 데이터 UI 반영)
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            scheduleViewModel.scheduledLectures.collectLatest { lectures ->
//                Log.d("Schedule", "Updated lectures: $lectures")
//                lectureAdapter.submitList(lectures)
//            }
//        }
//
//        // 동일한 데이터 구독이 중복되어 있는데, 아래는 필요에 따라 제거 가능
//        viewLifecycleOwner.lifecycleScope.launch {
//            scheduleViewModel.scheduledLectures.collectLatest { lectures ->
//                Log.d("ScheduleFragment", "lectures updated: $lectures")
//                lectureAdapter.submitList(lectures)
//            }
//        }
//
//        return view
//    }
//
//    companion object {
//        // ScheduleFragment 인스턴스 생성 팩토리 메서드
//        fun newInstance(): ScheduleFragment {
//            return ScheduleFragment()
//        }
//    }
//}

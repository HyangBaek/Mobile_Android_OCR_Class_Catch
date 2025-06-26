//package com.example.ocrclasscatchproject.presentation.ocr
//
//import android.app.Activity
//import android.content.Intent
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.lifecycleScope
//import com.example.ocrclasscatchproject.presentation.MainActivity
//import com.example.ocrclasscatchproject.databinding.FragmentOcrBinding
//import com.example.ocrclasscatchproject.presentation.schedule.ScheduleFragment
//import com.example.ocrclasscatchproject.presentation.schedule.ScheduleViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//@AndroidEntryPoint
//class OcrFragment : Fragment() {
//
//    // ViewBinding 객체 (프래그먼트 뷰와 연결)
//    private var _binding: FragmentOcrBinding? = null
//    private val binding get() = _binding!!
//
//    // OCR 기능 관련 ViewModel (Activity 범위 공유)
//    private val ocrviewModel: OcrViewModel by activityViewModels()
//
//    // 시간표 정보 저장용 ViewModel (Activity 범위 공유)
//    private val scheduleViewModel: ScheduleViewModel by activityViewModels()
//
//    // 이미지 선택을 위한 Activity Result API 설정
//    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        uri?.let {
//            // 이미지 선택 시 ViewModel에 이미지 전달 → OCR 처리 시작
//            ocrviewModel.processImage(requireContext(), it)
//        }
//    }
//
//    // 프래그먼트의 뷰 생성
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View {
//        _binding = FragmentOcrBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    // 뷰가 생성된 후 호출
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        var hasResult = false
//
//        // [이미지 선택 버튼 클릭 시]
//        binding.btnSelectImage.setOnClickListener {
//            // 이미지 선택 창 열기
//            imagePickerLauncher.launch("image/*")
//        }
//
//        // [다음 버튼 클릭 시]
//        binding.btnNext.setOnClickListener {
//            // OCR 결과로부터 강의 리스트 가져오기
//            val lectures = ocrviewModel.uiState.value.scheduledLectures
//
//            // ScheduleViewModel에 시간표 설정
//            scheduleViewModel.setSchedule(lectures)
//
//            Log.d("OcrFragment", "ScheduleViewModel에 넘기는 lectures: $lectures")
//
//            // 강의 정보가 있을 경우, 다음 프래그먼트(ScheduleFragment)로 이동
//            if (lectures.isNotEmpty()) {
//                (activity as? MainActivity)?.navigateTo(ScheduleFragment.newInstance())
//            } else {
//                // 강의 정보가 없을 경우 안내 메시지 출력
//                Toast.makeText(context, "시간표를 먼저 인식해주세요", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        // ViewModel의 상태(uiState)를 관찰하여 UI 업데이트
//        viewLifecycleOwner.lifecycleScope.launch {
//            ocrviewModel.uiState.collectLatest { state ->
//                // [로딩 상태] 진행 중이면 ProgressBar 표시
//                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//
//                // [텍스트 결과 표시]
//                binding.textResult.text = state.resultText
//
//                // [이미지 결과 표시]
//                state.bitmap?.let {
//                    binding.imageView.setImageBitmap(it)
//                }
//
//                Log.d("OcrCheck", "ResultText: ${state.resultText}")
//                Log.d("OcrCheck", "btnNext visibility: ${binding.btnNext.visibility}")
//
//                if (state.scheduledLectures.isNotEmpty()) {
//                    // UI 업데이트
//                    binding.btnNext.isVisible = true
//
//                }
//                // OCR 결과가 있을 때만 textResult와 btnNext 보여주기
//                // 결과가 한 번이라도 유효했다면 true로 설정
//                binding.textResult.visibility = if (state.resultText.isNotBlank()) View.VISIBLE else View.GONE
//                binding.btnNext.visibility = if (state.resultText.isNotBlank()) View.VISIBLE else View.GONE
//            }
//        }
//    }
//
//    // 프래그먼트 뷰 제거 시 바인딩 해제
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//package com.example.ocrclasscatchproject.presentation.schedule
//
//import android.app.AlertDialog
//import android.app.TimePickerDialog
//import android.content.Context
//import android.os.Bundle
//import android.text.InputType
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.ocrclasscatchproject.util.AlarmUtils
//import com.example.ocrclasscatchproject.databinding.FragmentAlarmSettingBinding
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.presentation.ocr.adapter.AlarmOffset
//import com.example.ocrclasscatchproject.presentation.ocr.adapter.AlarmOffsetAdapter
//import com.google.android.material.button.MaterialButton
//import dagger.hilt.android.AndroidEntryPoint
//import java.time.LocalTime
//
//@AndroidEntryPoint
//class AlarmSettingFragment : Fragment() {
//
//    private var _binding: FragmentAlarmSettingBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var lecture: Lecture
//    private lateinit var alarmOffsetAdapter: AlarmOffsetAdapter
//
//    // 기본 알람 시간 설정 (9시 00분)
//    private var selectedTime: LocalTime = LocalTime.of(9, 0)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // 전달받은 강의 객체를 인자로 받음, 없으면 예외 발생
//        lecture = arguments?.getParcelable("lecture")
//            ?: throw IllegalArgumentException("Lecture not found")
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        // 뷰 바인딩 초기화
//        _binding = FragmentAlarmSettingBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Log.d("AlarmSetting", "onViewCreated called")
//        // 기본 알람 시간 표시 (포맷팅된 문자열)
//        binding.textAlarmTime.text = formatTime(selectedTime)
//
//        // 요일 토글 버튼들을 키:요일 문자열, 값:토글 버튼으로 매핑
//        val dayToggleMap: Map<String, MaterialButton> = mapOf(
//            "수" to binding.toggleWed,
//            "목" to binding.toggleThu,
//            "월" to binding.toggleMon,
//            "금" to binding.toggleFri,
//            "토" to binding.toggleSat,
//            "화" to binding.toggleTue,
//            "일" to binding.toggleSun
//        )
//
//        Log.d("AlarmSetting", "lecture.day = ${lecture.day}")
//        Log.d("AlarmSetting", "dayToggleMap keys = ${dayToggleMap.keys}")
//
//        // 강의 요일에 해당하는 토글 버튼 ON 처리
//        lecture.day?.forEach { day ->
//            val toggle = dayToggleMap[day]
//            val toggleIdName = toggle?.let { requireContext().resources.getResourceEntryName(it.id) }
//            Log.d("AlarmSetting", "Toggle for $day: $toggleIdName → 체크 상태 ON")
//            dayToggleMap[day]?.isChecked = true
//        }
//
//        // 시간 선택 버튼 클릭 시 시간 선택 다이얼로그 표시
//        binding.btnSelectTime.setOnClickListener {
//            showTimePicker()
//        }
//
//        // 기본 알람 시간 리스트 생성 (5분, 10분, 30분, 1시간 전)
//        val alarmOffsetList = mutableListOf(
//            AlarmOffset("5분 전", 5),
//            AlarmOffset("10분 전", 10),
//            AlarmOffset("30분 전", 30),
//            AlarmOffset("1시간 전", 60)
//        )
//
//        // 알람 오프셋 어댑터 생성 및 리사이클러뷰에 연결
//        alarmOffsetAdapter = AlarmOffsetAdapter(alarmOffsetList) { updatedItem ->
//            Log.d("알람선택", "${updatedItem.label}: ${updatedItem.isChecked}")
//            // 선택 상태를 ViewModel 등에 반영하고 싶으면 여기서 처리
//        }
//
//        binding.alarmOffsetRecyclerView.adapter = alarmOffsetAdapter
//        binding.alarmOffsetRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // 커스텀 알람 추가 버튼 클릭 시 분 단위 입력 받는 다이얼로그 표시
//        binding.btnAddCustom.setOnClickListener {
//            val input = EditText(requireContext()).apply {
//                inputType = InputType.TYPE_CLASS_NUMBER
//                hint = "분 단위 입력"
//            }
//
//            AlertDialog.Builder(requireContext())
//                .setTitle("커스텀 알람 추가")
//                .setView(input)
//                .setPositiveButton("추가") { _, _ ->
//                    val min = input.text.toString().toIntOrNull()
//                    if (min != null) {
//                        alarmOffsetAdapter.addCustomItem(min)  // 어댑터에 새 항목 추가 및 자동 체크
//                    }
//                }
//                .setNegativeButton("취소", null)
//                .show()
//        }
//
//        // 저장 버튼 클릭 시 선택된 요일과 시간으로 알람 등록
//        binding.btnSetAlarm.setOnClickListener {
//            val selectedDays = dayToggleMap.filterValues { it.isChecked }.keys.toList()
//
//            context?.let {
//                // 알람 등록 유틸 호출
//                AlarmUtils.scheduleLectureAlarm(it, lecture, selectedTime)
//                Toast.makeText(it, "${lecture.subject} 알람이 설정되었습니다", Toast.LENGTH_SHORT).show()
//
//                // 설정 완료 후 이전 화면으로 돌아가기
//                parentFragmentManager.popBackStack()
//            }
//        }
//
//        // 취소 버튼 클릭 시 단순히 이전 화면으로 돌아감
//        binding.btnCancel.setOnClickListener {
//            parentFragmentManager.popBackStack()
//        }
//    }
//
//    /**
//     * SharedPreferences에 선택된 알람 오프셋 저장
//     */
//    fun saveAlarmOffsets(context: Context, offsets: List<AlarmOffset>) {
//        val prefs = context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)
//        val editor = prefs.edit()
//        val minutesList = offsets.filter { it.isChecked }.map { it.minutes }
//        editor.putString("selected_offsets", minutesList.joinToString(","))
//        editor.apply()
//    }
//
//    /**
//     * 저장된 알람 오프셋 로드 (분 단위 리스트 반환)
//     */
//    fun loadAlarmOffsets(context: Context): List<Int> {
//        val prefs = context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)
//        val saved = prefs.getString("selected_offsets", "") ?: ""
//        return saved.split(",").mapNotNull { it.toIntOrNull() }
//    }
//
//    /**
//     * 시간 선택 다이얼로그 표시
//     */
//    private fun showTimePicker() {
//        val dialog = TimePickerDialog(
//            requireContext(),
//            { _, hourOfDay, minute ->
//                selectedTime = LocalTime.of(hourOfDay, minute)
//                binding.textAlarmTime.text = selectedTime.toString()
//            },
//            selectedTime.hour,
//            selectedTime.minute,
//            true
//        )
//        dialog.show()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    companion object {
//        // 인스턴스 생성 시 Lecture 객체 전달용 팩토리 메서드
//        fun newInstance(lecture: Lecture): AlarmSettingFragment {
//            val fragment = AlarmSettingFragment()
//            val bundle = Bundle()
//            bundle.putParcelable("lecture", lecture)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//
//    /**
//     * 시간을 "오전/오후 시 분" 형태로 포맷팅하여 반환
//     */
//    private fun formatTime(time: LocalTime): String {
//        val amPm = if (time.hour < 12) "오전" else "오후"
//        val hour12 = if (time.hour % 12 == 0) 12 else time.hour % 12
//        return "${amPm} ${hour12}시 ${time.minute}분"
//    }
//}
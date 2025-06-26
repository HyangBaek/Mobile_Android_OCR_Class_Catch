//package com.example.ocrclasscatchproject.presentation.schedule
//
//import android.app.TimePickerDialog
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.inputmethod.InputMethodManager
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import com.example.ocrclasscatchproject.R
//import com.example.ocrclasscatchproject.databinding.FragmentLectureDetailBinding
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.util.AlarmUtils
//import com.google.android.material.button.MaterialButton
//import com.google.android.material.button.MaterialButtonToggleGroup
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class LectureDetailFragment : Fragment() {
//
//    private lateinit var binding: FragmentLectureDetailBinding
//    private val scheduleViewModel: ScheduleViewModel by activityViewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        // ViewBinding 초기화
//        binding = FragmentLectureDetailBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        // Fragment 인자로 전달된 Lecture 객체 가져오기
//        val lecture = arguments?.getParcelable<Lecture>("lecture") ?: throw IllegalArgumentException("Lecture not found")
//        Log.d("LectureDetail", "Received lecture: $lecture")
//
//        // 액션바 뒤로가기 버튼 활성화
//        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        // 강의 정보 UI 초기화 (과목명, 강의실)
//        binding.editSubject.setText(lecture.subject)
//        binding.editRoom.setText(lecture.room)
//
//        // 과목명 EditText에 포커스 및 키보드 표시
//        binding.editSubject.requestFocus()
//        binding.editSubject.post {
//            binding.editSubject.requestFocus()
//            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(binding.editSubject, InputMethodManager.SHOW_IMPLICIT)
//        }
//
//        // 한글 요일을 영어 요일로 변환하여 토글 버튼 초기 선택값 설정
//        val koreanToEnglish = mapOf(
//            "월" to "Mon", "화" to "Tue", "수" to "Wed",
//            "목" to "Thu", "금" to "Fri", "토" to "Sat", "일" to "Sun"
//        )
//        val selectedDays = lecture.day?.mapNotNull { koreanToEnglish[it] } ?: emptyList()
//        setupDayToggle(view, selectedDays)
//
//        // 시작 시간, 종료 시간 텍스트뷰 초기화 ("09:00" 등)
//        binding.textStartTime.text = "${lecture.startHour.padStart(2, '0')}:00"
//        binding.textEndTime.text = "${lecture.endHour.padStart(2, '0')}:00"
//
//        // 시작 시간 텍스트뷰 클릭 시 시간 선택 다이얼로그 표시
//        binding.textStartTime.setOnClickListener {
//            val current = binding.textStartTime.text.toString().split(":").map { it.toInt() }
//            TimePickerDialog(requireContext(), { _, hour, minute ->
//                binding.textStartTime.text = String.format("%02d:%02d", hour, minute)
//            }, current[0], current[1], true).show()
//        }
//
//        // 종료 시간 텍스트뷰 클릭 시 시간 선택 다이얼로그 표시
//        binding.textEndTime.setOnClickListener {
//            val current = binding.textEndTime.text.toString().split(":").map { it.toInt() }
//            TimePickerDialog(requireContext(), { _, hour, minute ->
//                binding.textEndTime.text = String.format("%02d:%02d", hour, minute)
//            }, current[0], current[1], true).show()
//        }
//
//        // 알람 설정 버튼 클릭 시 AlarmSettingFragment 로 이동
//        binding.btnAlarmSetting.setOnClickListener {
//            val alarmFragment = AlarmSettingFragment.newInstance(lecture)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, alarmFragment)
//                .addToBackStack(null)
//                .commit()
//        }
//
//        // 알람 ON/OFF 스위치 이벤트 처리
//        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                // 스위치 ON → 알람 설정 화면으로 이동
//                val alarmFragment = AlarmSettingFragment.newInstance(lecture)
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.fragment_container, alarmFragment)
//                    .addToBackStack(null)
//                    .commit()
//            } else {
//                // 스위치 OFF → 알람 해제
//                AlarmUtils.cancelLectureAlarm(requireContext(), lecture)
//            }
//        }
//
//        // 알람 설정 여부 저장 (SharedPreferences)
//        val prefs = requireContext().getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)
//        prefs.edit().putBoolean("lecture_${lecture.id}_alarm_enabled", true).apply()
//
//        // 수정 버튼 클릭 시
//        binding.btnUpdate.setOnClickListener {
//            // 입력한 과목명, 강의실, 시작/종료 시간, 선택된 요일 정보 가져오기
//            val newSubject = binding.editSubject.text.toString()
//            val newRoom = binding.editRoom.text.toString()
//            val newStartHour = binding.textStartTime.text.toString().split(":")[0] // "09"
//            val newEndHour = binding.textEndTime.text.toString().split(":")[0]     // "10"
//            Log.d("LectureDetail", "저장할 시간: ${newStartHour}~${newEndHour}")
//
//            val selectedDays = getSelectedDays(view)
//
//            // 수정된 Lecture 객체 생성 (기존 id 유지)
//            val updatedLecture = Lecture(
//                id = lecture.id,
//                day = selectedDays,
//                startHour = newStartHour,
//                endHour = newEndHour,
//                subject = newSubject,
//                room = newRoom
//            )
//            // 기존 리스트 가져오기
//            val currentList = scheduleViewModel.scheduledLectures.value.toMutableList()
//            val index = currentList.indexOfFirst { it.id == lecture.id }
//
//            // ViewModel에 수정된 강의 정보 전달
//            scheduleViewModel.updateLecture(updatedLecture)
//            Log.d("LectureDetail", "강의 수정됨: $updatedLecture")
//
//            // 수정 완료 후 이전 화면으로 복귀
//            parentFragmentManager.popBackStack()
//        }
//
//        // 삭제 버튼 클릭 시 (구현 필요)
//        binding.btnDelete.setOnClickListener {
//            // 삭제 로직 추가 예정
//        }
//
//        // 공유 버튼 클릭 시 강의명과 강의실 정보 텍스트로 공유 인텐트 실행
//        binding.btnShare.setOnClickListener {
//            val shareIntent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, "${lecture.subject} - ${lecture.room}")
//                type = "text/plain"
//            }
//            startActivity(Intent.createChooser(shareIntent, "공유하기"))
//        }
//
//        // 알람 버튼 클릭 시 AlarmSettingFragment 로 이동
//        binding.btnAlarm.setOnClickListener {
//            Log.d("LectureDetail", "Alarm 버튼 클릭됨")
//            val alarmFragment = AlarmSettingFragment.newInstance(lecture)
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, alarmFragment)
//                .addToBackStack(null)
//                .commit()
//        }
//    }
//
//    companion object {
//        // 인스턴스 생성 시 Lecture 객체를 인자로 넘김
//        fun newInstance(lecture: Lecture): LectureDetailFragment {
//            return LectureDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putParcelable("lecture", lecture)
//                }
//            }
//        }
//    }
//
//    /**
//     * 요일 토글 버튼 초기 선택 및 변경 리스너 설정 함수
//     * @param view root 뷰
//     * @param selectedDays 선택된 영어 요일 리스트
//     */
//    private fun setupDayToggle(view: View, selectedDays: List<String> = emptyList()) {
//        val days = mapOf(
//            "Mon" to R.id.btnMon,
//            "Tue" to R.id.btnTue,
//            "Wed" to R.id.btnWed,
//            "Thu" to R.id.btnThu,
//            "Fri" to R.id.btnFri,
//            "Sat" to R.id.btnSat,
//            "Sun" to R.id.btnSun
//        )
//
//        // 선택된 요일에 해당하는 버튼 체크 처리
//        selectedDays.forEach { day ->
//            days[day]?.let { id ->
//                view.findViewById<MaterialButton>(id)?.isChecked = true
//            }
//        }
//
//        // 토글 버튼 그룹 체크 상태 변경 리스너
//        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.daySelector)
//        toggleGroup.addOnButtonCheckedListener { _, _, _ ->
//            val selected = mutableListOf<String>()
//            for ((day, id) in days) {
//                val button = view.findViewById<MaterialButton>(id)
//                if (button.isChecked) {
//                    selected.add(day)
//                }
//            }
//            // 선택된 요일 리스트 출력 또는 저장
//            Log.d("SelectedDays", selected.joinToString(", "))
//            // 여기에 필요한 작업 추가 (예: ViewModel 업데이트 등)
//        }
//    }
//
//    /**
//     * 현재 선택된 요일을 영어 요일 리스트로 반환
//     */
//    fun getSelectedDays(view: View): List<String> {
//        val dayMap = mapOf(
//            R.id.btnMon to "Mon",
//            R.id.btnTue to "Tue",
//            R.id.btnWed to "Wed",
//            R.id.btnThu to "Thu",
//            R.id.btnFri to "Fri",
//            R.id.btnSat to "Sat",
//            R.id.btnSun to "Sun"
//        )
//
//        return dayMap.filter { (id, _) ->
//            view.findViewById<MaterialButton>(id).isChecked
//        }.values.toList()
//    }
//
//}

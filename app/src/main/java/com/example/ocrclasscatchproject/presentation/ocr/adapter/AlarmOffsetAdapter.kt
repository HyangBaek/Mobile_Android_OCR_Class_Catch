//package com.example.ocrclasscatchproject.presentation.ocr.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.ocrclasscatchproject.databinding.ItemAlarmOffsetBinding
//import com.example.ocrclasscatchproject.domain.ocr.model.AlarmTimeItem
//
///**
// * RecyclerView 항목으로 사용할 알람 시간 정보 클래스
// *
// * @param label 사용자에게 표시될 문자열 (예: "10분 전")
// * @param minutes 알람 오프셋 (분 단위)
// * @param isChecked 체크 여부
// */
//data class AlarmOffset(
//    val label: String,
//    val minutes: Int,
//    var isChecked: Boolean = false
//)
//
///**
// * 알람 시간 선택 RecyclerView 어댑터
// *
// * @param alarmTimes 알람 항목 리스트
// * @param onCheckedChanged 항목 체크 상태가 변경될 때 호출되는 콜백
// */
//class AlarmOffsetAdapter(
//    private val alarmTimes: MutableList<AlarmOffset>,
//    private val onCheckedChanged: (AlarmOffset) -> Unit
//) : RecyclerView.Adapter<AlarmOffsetAdapter.ViewHolder>() {
//
//    /**
//     * ViewHolder 클래스: 각 RecyclerView 항목에 데이터 바인딩 처리
//     */
//    inner class ViewHolder(private val binding: ItemAlarmOffsetBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        /**
//         * 알람 항목을 뷰에 바인딩하고 체크 이벤트 설정
//         */
//        fun bind(item: AlarmOffset) {
//            binding.checkbox.text = item.label
//            binding.checkbox.isChecked = item.isChecked
//
//            // 기존 리스너 제거: onBindViewHolder 호출 시 중복 이벤트 방지
//            binding.checkbox.setOnCheckedChangeListener(null)
//
//            // 새 리스너 설정: 체크 상태 변경 시 호출
//            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
//                item.isChecked = isChecked
//                onCheckedChanged(item)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemAlarmOffsetBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return ViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int = alarmTimes.size
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(alarmTimes[position])
//    }
//
//    /**
//     * 커스텀 알람 시간을 추가하는 함수
//     *
//     * @param minutesBefore 몇 분 전인지
//     */
//    fun addCustomItem(minutesBefore: Int) {
//        val label = "${minutesBefore}분 전"
//        val newItem = AlarmOffset(label, minutesBefore, isChecked = true)
//        alarmTimes.add(newItem)
//        notifyItemInserted(alarmTimes.size - 1)
//    }
//
//    /**
//     * 현재 체크된 알람 항목 리스트 반환
//     */
//    fun getSelectedItems(): List<AlarmOffset> {
//        return alarmTimes.filter { it.isChecked }
//    }
//}
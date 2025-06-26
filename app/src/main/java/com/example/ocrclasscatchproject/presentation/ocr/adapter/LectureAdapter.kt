//package com.example.ocrclasscatchproject.presentation.ocr.adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.databinding.ItemScheduleCardBinding
//
///**
// * OCR로 인식된 강의 정보를 리스트 형태로 보여주는 RecyclerView 어댑터
// *
// * @param onItemClick 강의 항목 클릭 시 수행할 동작 (콜백)
// */
//class LectureAdapter(
//    private val onItemClick: (Lecture) -> Unit
//) : ListAdapter<Lecture, LectureAdapter.LectureViewHolder>(DiffCallback) {
//
//    /**
//     * RecyclerView ViewHolder 클래스
//     * 각 강의 데이터를 개별 카드 형태로 바인딩
//     */
//    inner class LectureViewHolder(private val binding: ItemScheduleCardBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        /**
//         * 실제 강의 데이터를 뷰에 반영
//         */
//        fun bind(item: Lecture) {
//            binding.tvSubject.text = item.subject
//            binding.tvDayTime.text = "${item.day} ${item.startHour}~${item.endHour}"
//            binding.tvRoom.text = item.room
//
//            // 로그로 바인딩된 강의 정보 확인
//            Log.d("LectureAdapter", "bind: ${item}")
//
//            // 클릭 이벤트 처리
//            binding.root.setOnClickListener {
//                onItemClick(item)
//            }
//        }
//    }
//
//    /**
//     * ViewHolder 생성 (레이아웃 inflate)
//     */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemScheduleCardBinding.inflate(inflater, parent, false)
//        return LectureViewHolder(binding)
//    }
//
//    /**
//     * ViewHolder에 데이터 바인딩
//     */
//    override fun onBindViewHolder(holder: LectureViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    /**
//     * ListAdapter의 성능 최적화를 위한 DiffUtil 구현
//     * id가 같으면 동일 항목으로 간주
//     */
//    companion object DiffCallback : DiffUtil.ItemCallback<Lecture>() {
//        override fun areItemsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
//            return oldItem == newItem
//        }
//    }
//}

//package com.example.ocrclasscatchproject.presentation.ocr
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.widget.Toast
//
///**
// * 알람이 울릴 때 호출되는 BroadcastReceiver
// * 지정된 시간에 등록된 알람이 트리거되면 onReceive가 호출됨
// */
//class AlarmReceiver : BroadcastReceiver() {
//
//    /**
//     * 알람 수신 시 실행되는 콜백 메서드
//     * @param context - 현재 컨텍스트
//     * @param intent - 알람 관련 정보를 담은 Intent
//     */
//    override fun onReceive(context: Context, intent: Intent?) {
//        // 인텐트에서 강의명 추출, 없으면 기본값 "수업"
//        val lectureName = intent?.getStringExtra("lecture_name") ?: "수업"
//
//        // 간단한 토스트 메시지로 알림 표시
//        Toast.makeText(context, "$lectureName 시작 알림!", Toast.LENGTH_SHORT).show()
//    }
//}
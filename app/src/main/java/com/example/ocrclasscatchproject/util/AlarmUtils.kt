//package com.example.ocrclasscatchproject.util
//
//import android.app.*
//import android.content.*
//import com.example.ocrclasscatchproject.domain.ocr.model.Lecture
//import com.example.ocrclasscatchproject.presentation.ocr.AlarmReceiver
//import java.time.*
//import java.util.*
//
//object AlarmUtils {
//
//    /**
//     * 강의 알람 예약 함수
//     *
//     * @param context Context
//     * @param lecture Lecture 객체 (요일, 과목명 포함)
//     * @param time 알람 울릴 시간 (LocalTime)
//     *
//     * 강의 요일별로 알람을 반복 예약한다.
//     */
//    fun scheduleLectureAlarm(
//        context: Context,
//        lecture: Lecture,
//        time: LocalTime
//    ) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        // 강의 요일이 null일 수도 있으므로 안전하게 빈 리스트 처리
//        val days = lecture.day ?: listOf()
//
//        // 강의 요일마다 별도의 알람 예약
//        for (day in days) {
//            // 알람 발생 시 호출될 BroadcastReceiver 인텐트 생성
//            val intent = Intent(context, AlarmReceiver::class.java).apply {
//                putExtra("lecture_name", lecture.subject) // 알람 메시지용 과목명 전달
//                putExtra("lecture_day", day) // 필요 시 요일 정보도 전달
//            }
//
//            // 각 요일별 고유한 PendingIntent 생성 (requestCode 충돌 방지 위해 lecture와 day 해시 합산 사용)
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                (lecture.hashCode() + day.hashCode()), // 각 요일마다 고유한 requestCode 필요
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//            // Calendar 객체에 알람 울릴 요일과 시간 설정
//            val calendar = Calendar.getInstance().apply {
//                set(Calendar.DAY_OF_WEEK, dayToCalendarDay(day)) // 요일 변환
//                set(Calendar.HOUR_OF_DAY, time.hour) // 시
//                set(Calendar.MINUTE, time.minute)   // 분
//                set(Calendar.SECOND, 0)
//                set(Calendar.MILLISECOND, 0)
//
//                // 만약 설정한 시간이 현재 시각보다 과거라면 다음 주 동일 요일로 미룸
//                if (before(Calendar.getInstance())) {
//                    add(Calendar.WEEK_OF_YEAR, 1)
//                }
//            }
//
//            // 매주 같은 요일/시간에 반복 알람 예약 (7일 간격)
//            alarmManager.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.timeInMillis,
//                AlarmManager.INTERVAL_DAY * 7,
//                pendingIntent
//            )
//        }
//    }
//
//    /**
//     * 한글 요일 문자열을 Calendar.DAY_OF_WEEK 상수로 변환
//     */
//    private fun dayToCalendarDay(day: String): Int {
//        return when (day) {
//            "일" -> Calendar.SUNDAY
//            "월" -> Calendar.MONDAY
//            "화" -> Calendar.TUESDAY
//            "수" -> Calendar.WEDNESDAY
//            "목" -> Calendar.THURSDAY
//            "금" -> Calendar.FRIDAY
//            "토" -> Calendar.SATURDAY
//            else -> Calendar.MONDAY // 기본값 월요일
//        }
//    }
//
//    /**
//     * 등록된 강의 알람을 취소하는 함수
//     *
//     * @param context Context
//     * @param lecture Lecture 객체
//     *
//     * 강의의 모든 요일에 대해 등록된 알람을 취소한다.
//     */
//    fun cancelLectureAlarm(context: Context, lecture: Lecture) {
//        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val days = lecture.day ?: listOf()
//
//        // 요일별로 PendingIntent 생성 후 알람 취소
//        for (day in days) {
//            val intent = Intent(context, AlarmReceiver::class.java).apply {
//                putExtra("lecture_name", lecture.subject)
//                putExtra("lecture_day", day)
//            }
//
//            val pendingIntent = PendingIntent.getBroadcast(
//                context,
//                (lecture.hashCode() + day.hashCode()),
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//
//            alarmManager.cancel(pendingIntent)
//        }
//    }
//}
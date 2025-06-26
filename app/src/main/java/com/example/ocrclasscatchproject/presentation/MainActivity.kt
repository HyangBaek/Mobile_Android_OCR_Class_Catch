package com.example.ocrclasscatchproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ocrclasscatchproject.R
//import com.example.ocrclasscatchproject.presentation.ocr.OcrFragment
import dagger.hilt.android.AndroidEntryPoint

// Hilt 의존성 주입을 위해 필요한 어노테이션
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 메인 레이아웃 설정
        setContentView(R.layout.activity_main)

        // savedInstanceState가 null인 경우 (앱 처음 실행 시에만)
        // OCR 기능을 담당하는 OcrFragment를 화면에 표시
        if (savedInstanceState == null) {
//            navigateTo(OcrFragment()) // 첫 화면으로 OCR 화면 설정
        }
    }

    // 다른 프래그먼트로 화면 전환할 때 사용
    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // 기존 프래그먼트를 새로운 프래그먼트로 교체
            .addToBackStack(null) // 뒤로가기 버튼 동작을 위해 백스택에 추가
            .commit() // 변경 사항 적용
    }
}

package com.example.paymoney.sign_up

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.example.paymoney.MainActivity
import com.example.paymoney.R
import com.example.paymoney.databinding.ActivityLoginBinding
import com.example.paymoney.databinding.ActivitySingUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //firebase 인스턴스 초기화
        auth = Firebase.auth


        binding.signUpButton.setOnClickListener {
            Social_login()
        }

    }



    //firebase 회원가입
    private fun Social_login() {
        val email = binding.signUpEmail.text.toString()
        val pw = binding.signUpPassword.text.toString()
        val pwCheck = binding.signUpCheckPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                Log.d("Create Check", task.toString())
                if (task.isSuccessful) {
                    //Firebase DB에 저장 되어 있는 계정이 아닐 경우 입력한 계정을 새로 등록한다
                    Toast.makeText(this,"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show()
                    goToMainActivity(task.result?.user)
                } else {
                    //입력한 계정 정보가 이미 Firebase DB에 있는 경우,로그인에 실패하면 사용자에게 메시지를 표시한다.
                    Toast.makeText(baseContext, "이미존재하는 계정이거나, 회원가입에 실패하였습니다..", Toast.LENGTH_SHORT,).show()
                }
            }
    }
    fun goToMainActivity(user: FirebaseUser?) {
        //Firebase에 등록된 계정일 경우에만 메인 화면으로 이동
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    //로그인 함수

}
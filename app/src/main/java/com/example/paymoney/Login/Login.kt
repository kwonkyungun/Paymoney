package com.example.paymoney.Login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paymoney.R
import com.example.paymoney.Sign_up.SignUp
import com.example.paymoney.databinding.ActivityLoginBinding
import com.example.paymoney.databinding.ActivityMainBinding
import java.security.MessageDigest

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //구글 로그인 버튼
        binding.btnGoogle.setOnClickListener {


        }
        //카카오 로그인 버튼
        binding.btnKakao.setOnClickListener {

        }

        //회원가입버튼
        binding.btnSignUp.setOnClickListener{
            val btnSignUp = Intent(this,SignUp::class.java)
            startActivity(btnSignUp)
        }

        //Paymoney로그인
        binding.btnPayLogin.setOnClickListener {

        }









    }
}
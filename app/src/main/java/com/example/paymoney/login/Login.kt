package com.example.paymoney.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paymoney.MainActivity
import com.example.paymoney.R
import com.example.paymoney.sign_up.SignUp
import com.example.paymoney.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

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

        //질문
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //firebase 인스턴스 초기화
        auth = Firebase.auth

        // kakao SDK 초기화
        KakaoSdk.init(this, "{bcb49a5cbb19c7b3250c810bea12038c}")


        //구글 로그인 버튼
        binding.btnGoogle.setOnClickListener {


        }
        //카카오 로그인 버튼
        binding.btnKakao.setOnClickListener {
            kakaoLogin()

        }

        //Paymoney로그인
        binding.btnPayLogin.setOnClickListener {
            PayMoneyLogin()

        }

        //회원가입버튼
        binding.btnSignUp.setOnClickListener{
            val btnSignUp = Intent(this,SignUp::class.java)
            startActivity(btnSignUp)
        }


    }

    // 카카오 로그인
    private fun kakaoLogin () {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    getkakaoUserInfo()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }

    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            getkakaoUserInfo()
        }
    }

    private fun getkakaoUserInfo () {
        // 사용자 정보 요청 (기본 동의)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                var name = user.kakaoAccount?.profile?.nickname
                var email = user.kakaoAccount?.email
                var number =  user.kakaoAccount?.phoneNumber
                Log.i(TAG, "사용자 정보 요청 성공 $user" )

                viewModel.saveLoginInfo(name, email, number)
//
            }
        }
    }


    private fun PayMoneyLogin (){
        val email = binding.LoginEmail.text.toString()
        val pw = binding.LoginPw.text.toString()

            auth?.signInWithEmailAndPassword(email, pw)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //로그인에 성공한 경우 메인 화면으로 이동
                        startActivity(Intent(this,MainActivity::class.java))
                    } else {
                        //로그인에 실패한 경우 Toast 메시지로 에러를 띄워준다
                        Toast.makeText(this, "로그인 실패하였습니다.", Toast.LENGTH_LONG).show()
                    }
        }
    }
}
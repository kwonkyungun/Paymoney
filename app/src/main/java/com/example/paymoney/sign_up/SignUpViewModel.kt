package com.example.paymoney.sign_up

import android.app.Application
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.paymoney.uiil.Constants.EMAIL
import com.example.paymoney.uiil.Constants.IS_LOGIN
import com.example.paymoney.uiil.Constants.PASSWORD
import com.example.paymoney.uiil.Constants.PHONE
import com.example.paymoney.uiil.Constants.POINT
import com.example.paymoney.uiil.Constants.ROLE
import com.example.paymoney.uiil.Constants.USER_NAME
import com.example.paymoney.uiil.RegexUtil
import com.example.paymoney.uiil.SharePrefUtil

class SignUpViewModel (app: Application) : AndroidViewModel(app) {


    private val shared = SharePrefUtil(app)

    private val name = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    private val phone = MutableLiveData<String>()
    private val role = MutableLiveData<String>()
    private val pwd = MutableLiveData<String>()
    private val pwdConfirm = MutableLiveData<String>()


    fun checkName(input: Editable?): Boolean {
        name.value = input?.trim().toString()
        return RegexUtil.checkName(input?.trim().toString())
    }

    fun checkEmail(input: Editable?): Boolean {
        email.value = input?.trim().toString()
        return RegexUtil.checkEmail(input?.trim().toString())
    }

    fun checkPhone(input: Editable?): Boolean {
        phone.value = input.toString()
        return RegexUtil.checkPhone(input.toString())
    }

    fun checkRole(item: String): Boolean {
        role.value = item
        return item.isNotEmpty()
    }

    fun checkPwd(input: Editable?): Boolean {
        pwd.value = input.toString()
        return RegexUtil.checkPassword(input.toString())
    }

    fun checkPwdConfirm(input: Editable?): Boolean {
        pwd.value?.let {
            pwdConfirm.value = input.toString()
            return RegexUtil.checkConfirmPassword(it, input.toString())
        }
        return false
    }

    fun saveFirstInfo() {
        shared.setStringPreferences(USER_NAME, name.value)
        shared.setStringPreferences(EMAIL, email.value)
        shared.setStringPreferences(PHONE, phone.value)
        shared.setStringPreferences(ROLE, role.value)
    }

    fun saveSecondInfo() {
        shared.setStringPreferences(PASSWORD, pwdConfirm.value)
        shared.setBooleanPreferences(IS_LOGIN, true)
        shared.setIntPreferences(POINT, 5000) // 5000 포인트 부여
    }

    fun getUserInfo(): String {
        val name = shared.getStringPreferences(USER_NAME, "")
        val email = shared.getStringPreferences(EMAIL, "")
        val phone = shared.getStringPreferences(PHONE, "")
        val role = shared.getStringPreferences(ROLE, "")
        val pwd = shared.getStringPreferences(PASSWORD, "")
        return "$name\n$email\n$phone\n$role\n$pwd"
    }
}


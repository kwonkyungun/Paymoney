package com.example.paymoney.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymoney.uiil.Constants
import com.example.paymoney.uiil.SharePrefUtil


class LoginViewModel(private val app: Application) : AndroidViewModel(app) {

    // preference 저장
    private var _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    fun saveLoginInfo(name:String?, email:String?, number: String? ) {
        val shared = SharePrefUtil(app)
        shared.setStringPreferences(Constants.USER_NAME, name)
        shared.setStringPreferences(Constants.EMAIL, email)
        shared.setStringPreferences(Constants.PHONE, number)

        _isSaved.postValue(true)
    }
}
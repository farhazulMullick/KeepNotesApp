package com.example.keepnotes.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun keyBoardManager(activity: Activity){
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}
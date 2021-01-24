package com.example.keepnotes.viewmodel

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.keepnotes.R

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val listener: AdapterView.OnItemSelectedListener = object :
    AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when(position){
                0 -> (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(application, R.color.red))

                1 -> (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(application, R.color.gold))

                2 -> (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(application, R.color.leafgreen))
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }

    val fabListener: RecyclerView.OnScrollListener = object :
            RecyclerView.OnScrollListener() {

    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return !TextUtils.isEmpty(title) || !TextUtils.isEmpty(description)
    }

}
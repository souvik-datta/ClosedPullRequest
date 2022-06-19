package com.souvik.naviapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.souvik.naviapp.R
import com.souvik.naviapp.ui.fragments.RepoFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, RepoFragment())
            .addToBackStack("RepoFragment")
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>1){
            supportFragmentManager.popBackStackImmediate()
        }else{
            finish()
        }
    }
}
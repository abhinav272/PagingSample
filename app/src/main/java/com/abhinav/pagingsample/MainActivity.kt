package com.abhinav.pagingsample

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        btn_github_demo.setOnClickListener {
            openGithubDemo()
        }

        btn_network_demo.setOnClickListener {
            openNewsDemo()
        }
    }

    private fun openNewsDemo() {
        startActivity(Intent(this, GithubDemoActivity::class.java))
    }

    private fun openGithubDemo() {
        startActivity(Intent(this, NewsDemoActivity::class.java))
    }
}
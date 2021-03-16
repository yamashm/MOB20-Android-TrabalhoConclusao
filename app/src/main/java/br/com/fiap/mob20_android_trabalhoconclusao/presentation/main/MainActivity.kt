package br.com.fiap.mob20_android_trabalhoconclusao.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.fiap.mob20_android_trabalhoconclusao.R
import android.view.Menu
import android.view.MenuItem
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.information.InformationActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // throw RuntimeException("Test Crash") // Force a crash para crashlytics
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item -> {
                startActivity(
                        Intent(this, InformationActivity::class.java)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
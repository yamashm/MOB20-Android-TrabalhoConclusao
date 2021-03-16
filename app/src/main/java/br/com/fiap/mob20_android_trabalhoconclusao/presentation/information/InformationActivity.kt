package br.com.fiap.mob20_android_trabalhoconclusao.presentation.information

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import br.com.fiap.mob20_android_trabalhoconclusao.R

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        findViewById<ImageView>(R.id.ivBack2).setOnClickListener {
            finish()
        }
    }
}
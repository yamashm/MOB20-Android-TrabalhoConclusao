package br.com.fiap.mob20_android_trabalhoconclusao.extensions

import android.widget.EditText

fun EditText.getString(): String {
    return this.text.toString()
}
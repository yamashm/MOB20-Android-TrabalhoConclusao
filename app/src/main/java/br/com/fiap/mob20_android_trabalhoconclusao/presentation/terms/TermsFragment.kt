package br.com.fiap.mob20_android_trabalhoconclusao.presentation.terms

import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.BaseFragment
import android.view.View


class TermsFragment : BaseFragment() {
    override val layout = R.layout.fragment_terms

    private lateinit var wvTerms: WebView
    private lateinit var ivBack: ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            wvTerms = view.findViewById(R.id.wvTerms)
            ivBack = view.findViewById(R.id.ivBack)
            ivBack.setOnClickListener { activity?.onBackPressed()
            }
            wvTerms.loadUrl("https://www.lipsum.com/")
    }
}
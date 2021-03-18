package br.com.fiap.mob20_android_trabalhoconclusao.presentation.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.concrete.canarinho.watcher.TelefoneTextWatcher
import br.com.concrete.canarinho.watcher.evento.EventoDeValidacao
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.CreateUserUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.BaseFragment
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpFragment : BaseFragment() {
    override val layout = R.layout.fragment_sign_up

    private lateinit var etUserNameSignUp : EditText
    private lateinit var etEmailSignUp : EditText
    private lateinit var etPhoneSignUp : EditText
    private lateinit var etPasswordSignUp : EditText
    private lateinit var cbTermsSignUp : LottieAnimationView
    private lateinit var btCreateAccount : Button
    private lateinit var btLoginSignUp : TextView
    private var checkBoxDone = false
    private lateinit var tvTerms: TextView


    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        registerObserver()

        tvTerms = view.findViewById( R.id.tvTerms)
        tvTerms.setOnClickListener {
            NavHostFragment .findNavController( this)
                .navigate( R.id.action_signUpFragment_to_termsFragment)
        }
    }

    private val signUpViewModel : SignUpViewModel by lazy {
        ViewModelProvider(
            this,
            SignUpViewModelFactory(
                CreateUserUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(Firebase.auth, Firebase.firestore)
                    )
                )
            )
        ).get( SignUpViewModel ::class.java)
    }

    private fun setUpView(view: View) {
        etUserNameSignUp = view.findViewById(R.id.etUserNameSignUp)
        etEmailSignUp = view.findViewById(R.id.etEmailSignUp)
        etPhoneSignUp = view.findViewById(R.id.etPhoneSignUp)
        etPasswordSignUp = view.findViewById(R.id.etPasswordSignUp)
        cbTermsSignUp = view.findViewById(R.id.cbTermsSignUp)
        tvTerms = view.findViewById(R.id.tvTerms)
        btCreateAccount = view.findViewById(R.id.btCreateAccount)
        btLoginSignUp = view.findViewById(R.id.btLoginSignUp)
        setUpListener()
    }

    private fun setUpListener() {
        etPhoneSignUp.addTextChangedListener(TelefoneTextWatcher(object : EventoDeValidacao {
            override fun totalmenteValido(valorAtual: String?) {}
            override fun invalido(valorAtual: String?, mensagem: String?) {}
            override fun parcialmenteValido(valorAtual: String?) {}
        }))
        tvTerms.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_signUpFragment_to_termsFragment)
        }
        btCreateAccount.setOnClickListener {
            signUpViewModel.create(
                etUserNameSignUp.text.toString(),
                etEmailSignUp.text.toString(),
                etPhoneSignUp.text.toString(),
                etPasswordSignUp.text.toString()
            )
        }
        btLoginSignUp.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.login_nav_graph)
        }
        setUpCheckboxListener()
    }

    private fun setUpCheckboxListener() {
        cbTermsSignUp.setOnClickListener {
            if (checkBoxDone) {
                cbTermsSignUp.speed = -1f
                cbTermsSignUp.playAnimation()
                checkBoxDone = false
            } else {
                cbTermsSignUp.speed = 1f
                cbTermsSignUp.playAnimation()
                checkBoxDone = true
            }
        }
    }

    private fun registerObserver() {
        this.signUpViewModel.newUserState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.main_nav_graph)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Realizando a autenticação")
            }
        })
    }
}
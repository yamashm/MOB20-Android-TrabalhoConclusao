package br.com.fiap.mob20_android_trabalhoconclusao.presentation.integration

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import br.com.fiap.mob20_android_trabalhoconclusaolib.alertdialog.CustomAlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IntegrationFragment : BaseAuthFragment(){
    override val layout = R.layout.fragment_integration

    private lateinit var tvInfoName: TextView
    private lateinit var tvInfoLocation: TextView
    private lateinit var tvInfoPhone: TextView
    private lateinit var tvInfoDescription: TextView
    private lateinit var tvInfoZipCode: TextView

    private lateinit var btCall : Button
    private lateinit var btShare : Button

    private val integrationViewModel: IntegrationViewModel by lazy{
        ViewModelProvider(
            this,
            IntegrationViewModelFactory(
                GetItemUseCase(
                    ItemRepositoryImpl(
                        ItemRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance())
                    )
                )
            )
        ).get( IntegrationViewModel ::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        setUpListener()
        registerObserver()

        val itemIdArg = arguments?.getString("itemId")

        if(itemIdArg != null){
            integrationViewModel.getItem(itemIdArg)
        }
    }

    private fun setUpView(view: View) {
        btCall = view.findViewById(R.id.btCall)
        btShare = view.findViewById(R.id.btShare)

        tvInfoName = view.findViewById(R.id.tvInfoName)
        tvInfoLocation = view.findViewById(R.id.tvInfoLocation)
        tvInfoPhone = view.findViewById(R.id.tvInfoPhone)
        tvInfoDescription = view.findViewById(R.id.tvInfoDescription)
        tvInfoZipCode = view.findViewById(R.id.tvInfoZipCode)
    }

    private fun setUpListener() {
        btCall.setOnClickListener {

            val dialog = CustomAlertDialog()

            dialog.showDialog(
                requireActivity(),
                R.raw.callbutton35178,
                getString(R.string.dialog_call),
                getString(R.string.dialog_call_message) + " " + tvInfoPhone.text + " ?",
                getString(R.string.dialog_ok),
                View.OnClickListener {
                    dialog.dismissDialog()

                    val dialIntent = Intent(Intent.ACTION_CALL)
                    dialIntent.data = Uri.parse("tel:" + tvInfoPhone.text)

                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                requireActivity(),
                                Manifest.permission.CALL_PHONE
                            )
                        ) {
                            Toast.makeText(activity, getString(R.string.give_call_permission), Toast.LENGTH_SHORT).show()
                        } else {
                            ActivityCompat.requestPermissions(requireActivity(),
                                arrayOf(Manifest.permission.CALL_PHONE),
                                42)
                        }
                    } else {
                        startActivity(dialIntent)
                    }

                },
                getString(R.string.dialog_cancel),
                View.OnClickListener {
                    dialog.dismissDialog()
                },
                false
            )

        }

        btShare.setOnClickListener {

            val content: String = tvInfoName.text.toString() +
                    "\n" + tvInfoZipCode.text.toString() +
                    "\n" + tvInfoLocation.text.toString() +
                    "\n" + tvInfoPhone.text.toString() +
                    "\n" + tvInfoDescription.text.toString()

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, content)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun registerObserver() {
        integrationViewModel.getItemState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Success -> {
                    hideLoading()

                    tvInfoName.text = it.data.name
                    tvInfoZipCode.text = it.data.zipCode
                    tvInfoLocation.text = it.data.location
                    tvInfoPhone.text = it.data.phone
                    tvInfoDescription.text = it.data.description
                }
                is RequestState.Error -> {
                    hideLoading()
                }
                is RequestState.Loading -> {
                    showLoading(getString(R.string.loading_message))
                }
            }
        })
    }
}
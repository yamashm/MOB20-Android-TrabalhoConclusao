package br.com.fiap.mob20_android_trabalhoconclusao.presentation.maps

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.mob20_android_trabalhoconclusao.R
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.ItemRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.ItemRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.data.repository.UserRepositoryImpl
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.ListItem
import br.com.fiap.mob20_android_trabalhoconclusao.domain.entity.RequestState
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetItemsUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.domain.usecases.GetUserLoggedUseCase
import br.com.fiap.mob20_android_trabalhoconclusao.presentation.base.auth.BaseAuthFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class MapsFragment : BaseAuthFragment() {
    override val layout = R.layout.fragment_maps

    val listItems:  MutableList<ListItem> = ArrayList()

    private val mapsViewModel: MapsViewModel by lazy{
        ViewModelProvider(
            this,
            MapsViewModelFactory(
                GetItemsUseCase(
                    ItemRepositoryImpl(
                        ItemRemoteFirebaseDataSourceImpl(
                            FirebaseAuth.getInstance(),
                            FirebaseFirestore.getInstance()
                        )
                    )
                )
            )
        ).get(MapsViewModel::class.java)
    }


    private val callback = OnMapReadyCallback { googleMap ->

        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        var addressGeocoding: MutableList<Address> = ArrayList()

        listItems.forEach{
            val address = it.location
             addressGeocoding = geocoder.getFromLocationName(address, 1)
            if(addressGeocoding.size > 0)
                googleMap.addMarker(MarkerOptions().position(LatLng(addressGeocoding.first().latitude, addressGeocoding.first().longitude)))
        }
        if(addressGeocoding.size > 0)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(addressGeocoding.first().latitude, addressGeocoding.first().longitude), 12f))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        mapsViewModel.getItems()
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapsFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun registerObserver() {
        mapsViewModel.getItemsState.observe(viewLifecycleOwner, Observer {
            when(it) {
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Success -> {
                    hideLoading()

                    for ((name, location, phone, description, zipCode, itemId, userId) in it.data) {
                        var item: ListItem = ListItem(name, location, phone, description, zipCode, itemId)

                        listItems.add(item)
                    }

                    setUpMap()
                }
            }
        })
    }
}
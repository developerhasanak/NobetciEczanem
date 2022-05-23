package com.hasan.nobetcieczanem.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ak.nobetcieczane.util.CityModel
import com.ak.nobetcieczane.util.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hasan.nobetcieczanem.R
import com.hasan.nobetcieczanem.api.RetrofitAPI
import com.hasan.nobetcieczanem.databinding.FragmentLoginBinding
import com.hasan.nobetcieczanem.model.CityResponse
import com.hasan.nobetcieczanem.model.CityResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginFragment:Fragment(R.layout.fragment_login),AdapterView.OnItemSelectedListener {
    lateinit var adapter1: ArrayAdapter<String>
    lateinit var adapter2: ArrayAdapter<String>
    private var spinneril = ArrayList<String>()
    private var spinnerilce = ArrayList<String>()
    private var city: List<CityModel>? = null
    lateinit var fragmentBinding:FragmentLoginBinding
    private var secileIlString :String? = null
    private var secilenIlceString:String?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentLoginBinding.bind(view)
        spinnerProcess(view)
        fragmentBinding.search.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToListFragment(secileIlString!!,fragmentBinding.spinner2.selectedItem.toString())
            Navigation.findNavController(view).navigate(action)
            spinneril.clear()
        }

        fragmentBinding.place.setOnClickListener {
          val action = LoginFragmentDirections.actionLoginFragmentToMapsFragment2()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun spinnerProcess(view: View) {
        val jsonFileString = getJsonDataFromAsset(view.context,"il_ilce.json")
        val gson = Gson()
        val listCityType = object : TypeToken<List<CityModel>>() {}.type
        city = gson.fromJson(jsonFileString, listCityType)
        city?.let {
            for (i in 0 until city!!.size) {
                spinneril.add(city!![i].il)
            }
        }
        adapter1 =
            ArrayAdapter(view.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,spinneril)
        adapter1.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        fragmentBinding.spinner1.adapter = adapter1
        fragmentBinding.spinner1.onItemSelectedListener = this@LoginFragment

        adapter2 =
            ArrayAdapter(view.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, spinnerilce)
        adapter2.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
       fragmentBinding.spinner2.adapter = adapter2
        //fragmentBinding.spinner2.onItemSelectedListener = this@LoginFragment


    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinnerilce.clear()
        val secilenil = p0?.getItemAtPosition(p2)
        secileIlString = secilenil.toString()
        try {
            city?.forEach {
                if (secilenil == it.il) {
                    it.ilceleri.forEach { ilce ->
                        spinnerilce.add(ilce)
                    }
                    adapter2.notifyDataSetChanged()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }







}
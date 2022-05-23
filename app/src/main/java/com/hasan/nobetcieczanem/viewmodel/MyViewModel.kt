package com.hasan.nobetcieczanem.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasan.nobetcieczanem.api.RetrofitBuilder.retrofitAPI
import com.hasan.nobetcieczanem.model.CityResponse
import com.hasan.nobetcieczanem.model.CityResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MyViewModel:ViewModel() {
 private var disposable: CompositeDisposable = CompositeDisposable()
 val response = MutableLiveData<CityResponse>()
 val eror = MutableLiveData<Boolean>()
 val loading = MutableLiveData<Boolean>()

 fun getDataApi(secilenilce:String,secilenil:String){


  disposable.add(retrofitAPI.getCity(secilenilce,secilenil)
   .subscribeOn(Schedulers.io())
   .observeOn(AndroidSchedulers.mainThread())
   .subscribeWith(object : DisposableSingleObserver<CityResponse>(){
    override fun onSuccess(t: CityResponse) {
     eror.value = false
     loading.value = false
     response.value = t


    }

       override fun onError(e: Throwable) {
        eror.value = true
        loading.value = false
        println(e.printStackTrace())

       }

     })
  )
 }


 override fun onCleared() {
  disposable.clear()
  super.onCleared()
 }



}
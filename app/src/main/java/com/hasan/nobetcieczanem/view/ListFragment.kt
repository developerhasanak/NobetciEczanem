package com.hasan.nobetcieczanem.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hasan.nobetcieczanem.viewmodel.MyViewModel
import com.hasan.nobetcieczanem.R
import com.hasan.nobetcieczanem.adapter.NobetciEczaneAdapter
import com.hasan.nobetcieczanem.databinding.FragmentListBinding

class ListFragment:Fragment(R.layout.fragment_list) {
   private lateinit var fragmentBinding:FragmentListBinding

    private lateinit var recyclerAdapter:NobetciEczaneAdapter
    private var secilenil:String? =null
    private var secilenilce:String? =null
    private lateinit var myViewModel: MyViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentListBinding.bind(view)

        arguments?.let {
            secilenil = ListFragmentArgs.fromBundle(it).il
            secilenilce = ListFragmentArgs.fromBundle(it).ilce
        }
        if (secilenil!=null || secilenilce!=null){
            myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
            myViewModel.getDataApi(secilenilce!!,secilenil!!)
        }
        fragmentBinding.eror.visibility=View.GONE
        fragmentBinding.swipRefresh.setOnRefreshListener {
            fragmentBinding.recyclerViewList.visibility = View.GONE
            fragmentBinding.eror.visibility = View.GONE
            fragmentBinding.loading.visibility = View.GONE
            myViewModel.getDataApi(secilenilce!!,secilenil!!)
            fragmentBinding.recyclerViewList.visibility =View.VISIBLE
            fragmentBinding.swipRefresh.isRefreshing =false
        }




        observeLiveData()

    }


    private fun observeLiveData(){
        myViewModel.response.observe(viewLifecycleOwner, Observer { cityResponse->

            cityResponse.let {
                if (cityResponse.success) {
                    fragmentBinding.recyclerViewList.layoutManager =
                        LinearLayoutManager(requireContext())
                    recyclerAdapter = NobetciEczaneAdapter(it.result)
                    fragmentBinding.recyclerViewList.adapter = recyclerAdapter

                }
            }

        })

       myViewModel.eror.observe(viewLifecycleOwner, Observer {
           if(it){
               fragmentBinding.eror.visibility = View.VISIBLE
               fragmentBinding.loading.visibility = View.GONE
               fragmentBinding.recyclerViewList.visibility = View.GONE

           }else{
               fragmentBinding.eror.visibility = View.GONE
           }
       })
      myViewModel.loading.observe(viewLifecycleOwner, Observer {
          if(it){
              fragmentBinding.loading.visibility =View.VISIBLE
              fragmentBinding.recyclerViewList.visibility = View.GONE
              fragmentBinding.eror.visibility = View.GONE
          }else{
              fragmentBinding.loading.visibility = View.GONE
          }
      })


    }




}
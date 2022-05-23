package com.hasan.nobetcieczanem.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hasan.nobetcieczanem.databinding.ListRowBinding
import com.hasan.nobetcieczanem.model.CityResult
import com.hasan.nobetcieczanem.view.ListFragmentDirections

class NobetciEczaneAdapter(val eczaneList:List<CityResult>):RecyclerView.Adapter<NobetciEczaneAdapter.NobetciEzcaneHolder>() {
    class NobetciEzcaneHolder(val binding: ListRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NobetciEzcaneHolder {
        val binding = ListRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NobetciEzcaneHolder(binding)
    }

    override fun onBindViewHolder(holder: NobetciEzcaneHolder, position: Int) {

        holder.binding.veriler = eczaneList[position]
        /*holder.binding.name.text = eczaneList[position].name
        holder.binding.dist.text = eczaneList[position].dist
        holder.binding.address.text =eczaneList[position].address
        holder.binding.phone.text = eczaneList[position].phone

         */
        holder.binding.phoneAction.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.setData(Uri.parse("tel:0${eczaneList[position].phone}"))
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.placeAction.setOnClickListener {
             val action = ListFragmentDirections.actionListFragmentToMapsFragment(eczaneList[position].loc,eczaneList[position].name)
             Navigation.findNavController(it).navigate(action)
        }
       holder.binding.shareAction.setOnClickListener {
           val intent = Intent().apply {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TEXT,eczaneList[position].address)
               type = "text/plain"
           }
           val shareIntent = Intent.createChooser(intent,null)
           holder.itemView.context.startActivity(shareIntent)
       }




    }

    override fun getItemCount(): Int {
        return eczaneList.size
    }
}
package com.arunsoorya.mypocket.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.arunsoorya.mypocket.R
import com.arunsoorya.mypocket.sync.PsmsVo
import kotlinx.android.synthetic.main.sms_list_item.view.*

class SmsAdapter(val context: Context?, val items : List<PsmsVo>) : Adapter<SmsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsAdapter.ViewHolder {

        val inflatedView = LayoutInflater.from(context).inflate(R.layout.sms_list_item, parent,false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: SmsAdapter.ViewHolder, position: Int) {
        val psmsVo: PsmsVo = items.get(position)
         holder.placeholdeText.text = "#"
//         holder.title.text = psmsVo.person
//         holder.content.text = psmsVo.subject
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       var placeholdeText = itemView.placeholder_text
       var title = itemView.title
       var content = itemView.content
    }
}
package com.diyantech.chattyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diyantech.chattyapp.ModelClass.countrymodel.ModelFavorite
import com.diyantech.chattyapp.R
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteAdapter(private val mList: List<ModelFavorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_list_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mList[position]

        holder.civ_user_profile?.setImageResource(model.civ_image)
        holder.img_online_offline?.setImageResource(model.image)
        holder.txt_user_name?.text = model.text
        holder.txt_new_chat?.text = model.textNew
        holder.txt_time?.text = model.textTime
        holder.txt_message_count?.text = model.textMsgCount
        holder.rel_message_count?.text = model.textRelCount
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var civ_user_profile: CircleImageView? = null
        var img_online_offline: ImageView? = null
        var txt_user_name: TextView? = null
        var txt_new_chat:TextView? = null
        var txt_time:TextView? = null
        var txt_message_count:TextView? = null
        var rel_message_count: TextView? = null


    }
}
package com.diyantech.chattyapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diyantech.chattyapp.ModelClass.countrymodel.ModelFavorite
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.adapter.FavoriteAdapter

class FavoriteFragment : Fragment() {

     var rcv_favourite : RecyclerView?= null
    @SuppressLint("UseRequireInsteadOfGet", "WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        rcv_favourite = view?.findViewById(R.id.rcv_favourite)

        rcv_favourite?.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
        val data = ArrayList<ModelFavorite>()


        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))
        data.add(ModelFavorite(R.drawable.profile,R.drawable.ig_online_logo,"Brijesh Prajapati","Jordan doe" , "1m ago","2","2"))

        val adapter = FavoriteAdapter(data)

        rcv_favourite?.adapter = adapter

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}






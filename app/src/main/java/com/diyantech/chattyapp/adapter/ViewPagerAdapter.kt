package com.diyantech.chattyapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.diyantech.chattyapp.fragment.*

class ViewPagerAdapter(private val myContext: Context, fm: FragmentManager?, var totalTabs: Int) :
    FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChatsFragment()
            }
            1 -> {
                StoryFragment()
            }
            2 -> {
                GroupFragment()
            }
            3 -> {
                FavoriteFragment()
            }
            4 -> {
                UnreadFragment()
            }
            else -> ChatsFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
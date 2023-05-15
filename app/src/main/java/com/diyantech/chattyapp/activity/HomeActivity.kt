package com.diyantech.chattyapp.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.diyantech.chattyapp.ModelClass.countrymodel.modelotp.Data
import com.diyantech.chattyapp.R
import com.diyantech.chattyapp.adapter.ViewPagerAdapter
import com.diyantech.chattyapp.api.ApiUtility.Userdata
import com.diyantech.chattyapp.util.Session
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import de.hdodenhof.circleimageview.CircleImageView


class HomeActivity : AppCompatActivity() {
    private var pressedTime: Long = 0
    lateinit var civ_profile : CircleImageView
    var txt_title: TextView? = null
    var view_pager: ViewPager? = null
    var tab_bottom_layout: TabLayout? = null
    var img_search: ImageView? = null
    var img_message:ImageView? = null
    var img_more:ImageView? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var mBundle: Bundle? = null
    var Str_image: String? = null
    var Str_user:String? = null
    var Str_mobile:String? = null
    var userdata : Data?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.white)

        civ_profile = findViewById(R.id.civ_profile)
        txt_title = findViewById(R.id.txt_title)
        view_pager = findViewById(R.id.viewPager)
        tab_bottom_layout = findViewById(R.id.tab_bottom_layout)
        img_more = findViewById(R.id.img_more)
        img_message = findViewById(R.id.img_message)


        if (Session.getCurrentUser() != null){
            userdata = Session.getCurrentUser()

            Str_image = userdata?.profile_image
            Str_user = userdata?.userName
            Str_mobile = userdata?.mobileNo
        }
        val image: String = "http://122.170.0.3:3017/uploads/photos/" + Str_image
        Log.d("mTAG", "image-->$image")

        Glide.with(this)
            .load(image)
            .error(R.drawable.profile)
            .into(civ_profile)

        var user : Data?= null
        if (Session.getCurrentUser() != null){
            user = Session.getCurrentUser()!!
            Log.d("TAG", "onCreate: --->${user._id}")
            Log.d("TAG", "onCreate: --->${user.userName}")
            Log.d("TAG", "onCreate: --->${user.countryName}")
            Log.d("TAG", "onCreate: --->${user.countryCode}")
            Log.d("TAG", "onCreate: --->${user.profile_image}")
            Log.d("TAG", "onCreate: --->${user.mobileNo}")
        }

        tab_bottom_layout?.addTab(tab_bottom_layout?.newTab()!!.setText("Chats"))
        tab_bottom_layout?.addTab(tab_bottom_layout?.newTab()!!.setText("Story"))
        tab_bottom_layout?.addTab(tab_bottom_layout?.newTab()!!.setText("Group"))
        tab_bottom_layout?.addTab(tab_bottom_layout?.newTab()!!.setText("Favorite"))
        tab_bottom_layout?.addTab(tab_bottom_layout?.newTab()!!.setText("Unread"))
        tab_bottom_layout?.setTabGravity(TabLayout.GRAVITY_FILL)

        //get each tab from tabLayout

        //get each tab from tabLayout
        val tab0 = tab_bottom_layout?.getTabAt(0)
        val tab1 = tab_bottom_layout?.getTabAt(1)
        val tab2 = tab_bottom_layout?.getTabAt(2)
        val tab3 = tab_bottom_layout?.getTabAt(3)
        val tab4 = tab_bottom_layout?.getTabAt(4)

        //and set for each one a custom View

        //and set for each one a custom View
        tab0?.setCustomView(createCustomTabView("Chats", 22, R.color.pos_1)) //initially this tab is selected
        tab1?.setCustomView(createCustomTabView("Story", 14, R.color.white))
        tab2?.setCustomView(createCustomTabView("Group", 14, R.color.white))
        tab3?.setCustomView(createCustomTabView("Favorite", 14, R.color.white))
        tab4?.setCustomView(createCustomTabView("Unread", 14, R.color.white))


        val viewPagerAdapter = ViewPagerAdapter(this@HomeActivity, supportFragmentManager, tab_bottom_layout!!.getTabCount())
        view_pager?.setAdapter(viewPagerAdapter)
        view_pager?.addOnPageChangeListener(TabLayoutOnPageChangeListener(tab_bottom_layout))
        tab_bottom_layout?.tabRippleColor = null
        tab_bottom_layout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            @SuppressLint("ResourceType")
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager?.setCurrentItem(tab.position)
                when (tab.position) {
                    0 -> {
                        //tab_bottom_layout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.pos_1));
                        setTabTextSize(tab, 22, R.color.pos_1)
                        txt_title?.setText("Recent Chats")

                        // Stop The Tooltip...
                        for (i in 0 until tab_bottom_layout!!.tabCount) {
                            tab_bottom_layout?.getTabAt(i)?.view?.let { tabView ->
                                TooltipCompat.setTooltipText(tabView, null)
                            }
                        }
                    }
                    1 -> {
                        //tab_bottom_layout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.pos_3));
                        setTabTextSize(tab, 22, R.color.pos_3)
                        txt_title?.setText("Stories")

                        // Stop The Tooltip...
                        for (i in 0 until tab_bottom_layout!!.tabCount) {
                            tab_bottom_layout?.getTabAt(i)?.view?.let { tabView ->
                                TooltipCompat.setTooltipText(tabView, null)
                            }
                        }
                    }
                    2 -> {
                        //tab_bottom_layout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.pos_4));
                        setTabTextSize(tab, 22, R.color.pos_4)
                        txt_title?.setText("Group")

                        // Stop The Tooltip...
                        for (i in 0 until tab_bottom_layout!!.tabCount) {
                            tab_bottom_layout?.getTabAt(i)?.view?.let { tabView ->
                                TooltipCompat.setTooltipText(tabView, null)
                            }
                        }
                    }
                    3 -> {
                        //tab_bottom_layout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.pos_5));
                        setTabTextSize(tab, 22, R.color.pos_5)
                        txt_title?.setText("Favorite")

                        // Stop The Tooltip...
                        for (i in 0 until tab_bottom_layout!!.tabCount) {
                            tab_bottom_layout?.getTabAt(i)?.view?.let { tabView ->
                                TooltipCompat.setTooltipText(tabView, null)
                            }
                        }
                    }
                    4 -> {
                        //tab_bottom_layout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.pos_6));
                        setTabTextSize(tab, 22, R.color.pos_6)
                        txt_title?.setText("Unread")

                        // Stop The Tooltip...
                        for (i in 0 until tab_bottom_layout!!.tabCount) {
                            tab_bottom_layout?.getTabAt(i)?.view?.let { tabView ->
                                TooltipCompat.setTooltipText(tabView, null)
                            }
                        }
                    }
                    else -> {
                        setTabTextSize(tab, 22, R.color.pos_1)
                        txt_title?.setText("Recent Chats")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 ->
                        setTabTextSize(tab, 15, R.color.white)
                    1 ->
                        setTabTextSize(tab, 15, R.color.white)
                    2 ->
                        setTabTextSize(tab, 15, R.color.white)
                    3 ->
                        setTabTextSize(tab, 15, R.color.white)
                    4 ->
                        setTabTextSize(tab, 15, R.color.white)
                    else ->
                        setTabTextSize(tab, 15, R.color.white)
                }
                setTabTextSize(tab, 15, R.color.white)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        img_more?.setOnClickListener {
            val popup = PopupMenu(this@HomeActivity, img_more,
                Gravity.RIGHT or Gravity.RIGHT or Gravity.BOTTOM or Gravity.TOP)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
                var setting = findViewById<TextView>(R.id.setting)

            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.setting) {
                    setting.setTextColor(getColor(R.color.dialog_btn_background))
                    val intent = Intent(this@HomeActivity, SettingActivity::class.java)
                    startActivity(intent)
                }
                true
            }
            popup.show()
        }

        img_message?.setOnClickListener {
            showNewChatDialog()
        }

        civ_profile?.setOnClickListener {
            showProfileDialog()
        }

    }

    private fun showProfileDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.profile_dialog)


        val txt_cancel = dialog.findViewById<TextView>(R.id.txt_cancel)
        val img_profile = dialog.findViewById<CircleImageView>(R.id.img_profile)
        val img_edit = dialog.findViewById<ImageView>(R.id.img_edit)
        val txt_remove_profile = dialog.findViewById<TextView>(R.id.txt_remove_profile)
        val txt_phone_no = dialog.findViewById<EditText>(R.id.txt_phone_no)
        val txt_user_name = dialog.findViewById<EditText>(R.id.txt_user_name)

        txt_phone_no.setText(Session.getCurrentUser()?.mobileNo)
        txt_user_name.setText(Session.getCurrentUser()?.userName)

        Glide.with(this)
            .load("http://122.170.0.3:3017/uploads/photos/" + Session.getCurrentUser()?.profile_image)
            .error(R.drawable.profile)
            .into(img_profile)

        txt_cancel.setOnClickListener { dialog.dismiss() }

        dialog.show()
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }

    private fun showNewChatDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.new_chat)


        val txt_cancel = dialog.findViewById<TextView>(R.id.txt_cancel)
        val search_view = dialog.findViewById<EditText>(R.id.search_view)
        val rel_new_group = dialog.findViewById<RelativeLayout>(R.id.rel_new_group)
        val rel_add_new_contact = dialog.findViewById<RelativeLayout>(R.id.rel_add_new_contact)
//        val rcv_new_chat = dialog.findViewById<RecyclerView>(R.id.rcv_new_chat2)
        linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
//        rcv_new_chat.layoutManager = linearLayoutManager

        txt_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)


    }

    private fun setTabTextSize(tab: TabLayout.Tab, i: Int, pos_1: Int) {
        val tabCustomView = tab.customView
        val tabTextView = tabCustomView!!.findViewById<TextView>(R.id.tabTV)
        tabTextView.textSize = i.toFloat()
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.context, pos_1))

    }

    private fun createCustomTabView(s: String, i: Int, pos1: Int): View {
        val tabCustomView: View = layoutInflater.inflate(R.layout.custom_tab, null)
        val tabTextView = tabCustomView.findViewById<TextView>(R.id.tabTV)
        tabTextView.text = s
        tabTextView.textSize = i.toFloat()
        tabTextView.setTextColor(ContextCompat.getColor(tabCustomView.context, pos1 as Int))
        return tabCustomView

    }

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }


}
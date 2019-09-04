package com.example.kotlin_json

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlin_json.Adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_tab.*


class MainActivity : AppCompatActivity() {

    private val navLabels = arrayOf(Constant.POKEDEX_TAB, Constant.MOVE_TAB)

    private val navIcons =
        intArrayOf(R.drawable.ic_pokeball_tab, R.drawable.ic_fighting_game)

    private val navIconsActive =
        intArrayOf(R.drawable.ic_pokeball_tab_selected, R.drawable.ic_fighting_game_selected)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager_main.adapter = PagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)

        for (i in 0 until tabs_main.tabCount) {

            val tab = LayoutInflater.from(this).inflate(R.layout.nav_tab, null) as LinearLayout

            var TxtTabName = tab.findViewById<TextView>(R.id.nav_label)
            var TabIcon = tab.findViewById<ImageView>(R.id.nav_icon)

            TxtTabName.text = navLabels[i]
            TabIcon.setImageResource(navIcons[i])

            tabs_main.getTabAt(i)!!.customView = tab
        }
    }
}

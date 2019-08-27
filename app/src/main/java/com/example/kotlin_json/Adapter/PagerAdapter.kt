package com.example.kotlin_json.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.kotlin_json.Fragments.MoveListFragment
import com.example.kotlin_json.Fragments.PokemonListFragment


class PagerAdapter (manager: FragmentManager): FragmentPagerAdapter(manager){

    private val pages = listOf(
        PokemonListFragment(),
        MoveListFragment()
    )

    override fun getItem(p0: Int): Fragment {
        return pages[p0]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Pokedex List"
            else -> "Move List"
        }
    }

}
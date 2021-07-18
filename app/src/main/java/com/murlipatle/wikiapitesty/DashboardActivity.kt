package com.murlipatle.wikiapitesty

import android.os.Bundle
import android.view.Menu
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayoutMediator
import com.murlipatle.wikiapitesty.databinding.ActivityDashboardBinding
import com.murlipatle.wikiapitesty.ui.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val tabTitleList = listOf("Article", "Images", "Category")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.viewPager.adapter =
            ViewPagerAdapter(this)
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.theme_menu, menu)
        val menuItem = menu.findItem(R.id.my_switch)
        val mySwitch = menuItem.actionView as SwitchMaterial
        mySwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return true
    }

}
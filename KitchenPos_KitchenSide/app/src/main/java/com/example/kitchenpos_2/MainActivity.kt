package com.example.kitchenpos_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kitchenpos_2.ui.KitchenOrdersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load KitchenOrdersFragment full screen
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, KitchenOrdersFragment())
            .commit()
    }
}

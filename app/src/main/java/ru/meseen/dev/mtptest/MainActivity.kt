package ru.meseen.dev.mtptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.meseen.dev.mtptest.databinding.MainActivityBinding
import ru.meseen.dev.mtptest.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private val vb: MainActivityBinding by lazy { MainActivityBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}
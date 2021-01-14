package dev.b3nedikt.restring.example

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.restring.example.Utils.getJsonDataFromAsset

abstract class BaseActivity : AppCompatActivity() {

    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
                baseDelegate = super.getDelegate(),
                baseContext = this,
                wrapContext = { baseContext -> Restring.wrapContext(baseContext) }
        )
    }

    override fun getDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }
}

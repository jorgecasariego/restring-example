package dev.b3nedikt.restring.example

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.ContentFrameLayout
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.Reword
import java.util.*


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private lateinit var spinner: Spinner

    private lateinit var welcomeString: TextView
    private lateinit var stringArrayTextView: TextView
    private lateinit var stringNotInStringsXmlTextView: TextView
    private lateinit var quantityStringTextView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = view.findViewById(R.id.spinner)

        welcomeString = view.findViewById(R.id.welcomeString)
        stringArrayTextView = view.findViewById(R.id.stringArrayTextView)
        stringNotInStringsXmlTextView = view.findViewById(R.id.stringNotInStringsXmlTextView)
        quantityStringTextView = view.findViewById(R.id.quantityStringTextView)

        val localeStrings = AppLocale.supportedLocales.map { it.language + " " + it.country }
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line, localeStrings)

        spinner.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                AppLocale.desiredLocale = AppLocale.supportedLocales[position]

                // 1. Get strings from JSON
                // 2. Load String depending of selected language
                getJsonFromServer(AppLocale.desiredLocale)
                load()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun getJsonFromServer(desiredLocale: Locale) {
        var jsonFileString: String?
        when(desiredLocale.language) {
            "en" -> jsonFileString = Utils.getJsonDataFromAsset(activity!!.applicationContext, "example_en.json")
            "es" -> jsonFileString = Utils.getJsonDataFromAsset(activity!!.applicationContext, "example_es.json")
            else -> jsonFileString = Utils.getJsonDataFromAsset(activity!!.applicationContext, "example_en.json")
        }

        //Log.i("data", jsonFileString)

        //******************************************************************
        val gson = Gson()
        //val listPersonType = object : TypeToken<List<Example>>() {}.type

        //var persons: List<Example> = gson.fromJson(jsonFileString, listPersonType)
        var example: Example = gson.fromJson(jsonFileString, Example::class.java)
        example.data.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }

        //******************************************************************


        Restring.putStrings(desiredLocale, Utils.getStrings(example.data))
        //Restring.putQuantityStrings(desiredLocale, SampleStringsGenerator.getQuantityStrings(locale))
        Restring.putStringArrays(desiredLocale, Utils.getStringArrays(example.data, "options_days_of_week"))

    }

    fun load() {
        val rootView =
            requireActivity()
                .window
                .decorView
                .findViewById<ContentFrameLayout>(android.R.id.content)

        Reword.reword(rootView)

        stringNotInStringsXmlTextView.text = requireContext()
            .getStringResourceByName("title.welcome.back")


        stringArrayTextView.text = resources.getStringArray(R.array.options_days_of_week)
            .joinToString("\n")


//        quantityStringTextView.text = (0 until 3)
//            .joinToString("\n")
//            { resources.getQuantityString(R.plurals.quantity_string, it, it) }
    }
}

fun Context.getStringResourceByName(id: String): String? {
    val resId = resources.getIdentifier(id, "string", packageName)
    return if (resId != 0) {
        getString(resId)
    } else {
        null
    }
}

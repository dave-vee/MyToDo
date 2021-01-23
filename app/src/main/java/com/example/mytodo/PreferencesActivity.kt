package com.example.mytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class PreferencesActivity : AppCompatActivity() ,PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)


        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_settings,MainPreferences())
                .commit()
        }else{
            title = savedInstanceState.getCharSequence(TAG_TITLE)
        }



        supportFragmentManager.addOnBackStackChangedListener {
             if (supportFragmentManager.backStackEntryCount == 0){
                 setTitle(R.string.settings)
             }
        }

        setUpToolBar()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putCharSequence(TAG_TITLE,title)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.popBackStackImmediate()){
            true
        }
        return super.onSupportNavigateUp()
    }

    private fun setUpToolBar() {
        supportActionBar?.setTitle(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    class MainPreferences : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.main_preferences,rootKey)
        }
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        val args = pref?.extras

        val fragment = pref?.fragment.let {

            supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                it!!
                ).apply {
                    arguments = args
                    setTargetFragment(caller,0)
                }

        }

        fragment?.let{
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_settings,it)
                .addToBackStack(null)
                .commit()


        }

        title = pref?.title
        return true
    }

    companion object {
        private  val TAG_TITLE = "PREFERENCE_ACTIVITY"
    }

}
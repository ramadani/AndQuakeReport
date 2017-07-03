package id.ramadani.quake.ui.setting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceFragment
import id.ramadani.quake.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    companion object {
        class QuakePreferenceFragment : PreferenceFragment() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                addPreferencesFromResource(R.xml.settings_main)
            }
        }
    }
}

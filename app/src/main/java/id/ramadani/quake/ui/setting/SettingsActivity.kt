package id.ramadani.quake.ui.setting

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import id.ramadani.quake.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    companion object {
        class QuakePreferenceFragment : PreferenceFragment(),
                Preference.OnPreferenceChangeListener {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                addPreferencesFromResource(R.xml.settings_main)

                val minMag = findPreference(getString(R.string.settings_min_magnitude_key))
                bindPreferenceSummaryToValue(minMag)
            }

            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                val value = newValue.toString()
                preference?.summary = value
                return true
            }

            private fun bindPreferenceSummaryToValue(preference: Preference) {
                preference.onPreferenceChangeListener = this

                val preferences: SharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(preference.context)
                val prefString = preferences.getString(preference.key, "")
                onPreferenceChange(preference, prefString)
            }
        }
    }
}

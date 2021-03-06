package id.ramadani.quake.ui.quakes

import id.ramadani.quake.base.View
import id.ramadani.quake.data.model.Quake

/**
 * Created by dani on 6/15/17.
 */
interface QuakesViewContract : View {

    fun addToQuakeList(quakes: List<Quake>)

    fun openQuakeMap(quake: Quake)

    fun clearingQuakeList()

    fun toggleLoading(state: Boolean)

    fun toggleList(state: Boolean)

    fun toggleEmptyState(state: Boolean)
}

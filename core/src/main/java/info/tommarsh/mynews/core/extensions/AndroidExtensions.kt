package info.tommarsh.mynews.core.extensions

import android.view.Menu
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import info.tommarsh.mynews.core.repository.PreferencesRepository

//region listadapter
fun <T> getDiffUtilItemCallback(compare: (T, T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(old: T, new: T) = compare(old, new)

        override fun areContentsTheSame(old: T, new: T) = old?.equals(new) ?: false
    }
//endregion

//region menu
inline fun <reified T> Menu.getActionItem(item: Int): T = findItem(item).actionView as T
//endregion

//region Activity
inline fun <reified T> AppCompatActivity.service(type: String) = getSystemService(type) as T

fun AppCompatActivity.observeNightMode(preferencesRepository: PreferencesRepository) {
    preferencesRepository.observeNightModeChanges().observe(this, Observer { nightMode ->
        delegate.localNightMode = nightMode
        invalidateOptionsMenu()
    })
}

fun AppCompatActivity.contentBehindStatusBar() = with(window){
    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

    statusBarColor = ContextCompat.getColor(window.context, android.R.color.transparent)
}
//endregion

//region Fragment
fun Fragment.consume(block: () -> Unit) : Boolean {
    block()
    return false
}
//endregion
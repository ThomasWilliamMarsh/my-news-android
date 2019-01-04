package info.tommarsh.presentation.ui.base

import androidx.fragment.app.Fragment
import info.tommarsh.presentation.ViewModelFactory
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    protected lateinit var factory: ViewModelFactory
}
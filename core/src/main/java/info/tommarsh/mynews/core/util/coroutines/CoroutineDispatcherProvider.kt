package info.tommarsh.mynews.core.util.coroutines

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineDispatcherProvider
@Inject constructor() : DispatcherProvider {

    override fun main() = Dispatchers.Main

    override fun work() = Dispatchers.IO
}
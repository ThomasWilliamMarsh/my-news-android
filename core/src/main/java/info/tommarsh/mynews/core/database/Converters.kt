package info.tommarsh.mynews.core.database

import androidx.room.TypeConverter
import info.tommarsh.mynews.core.article.data.local.model.Source

class Converters internal constructor() {

    companion object {
        private const val NUM_FIELDS = 2
        private const val COLUMN_ID = 0
        private const val COLUMN_NAME = 1
    }

    @TypeConverter
    fun stringToSource(value: String): Source {
        val split = value.split(" ")
        return if (split.size == NUM_FIELDS) {
            Source(
                split[COLUMN_ID],
                split[COLUMN_NAME]
            )
        } else Source(" ", " ")
    }

    @TypeConverter
    fun sourceToString(source: Source) = "${source.id} ${source.name}"
}
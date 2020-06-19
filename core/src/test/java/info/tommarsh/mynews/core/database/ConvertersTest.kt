package info.tommarsh.mynews.core.database

import info.tommarsh.mynews.core.article.data.local.model.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    private val source = Source("id", "name")
    private val sourceString = "id name"

    @Test
    fun `Convert source to string`() {
        val expected = sourceString

        val actual = converters.sourceToString(source)

        assertEquals(expected, actual)
    }

    @Test
    fun `Convert string to source`() {
        val expected = source

        val actual = converters.stringToSource(sourceString)

        assertEquals(expected, actual)
    }
}
package info.tommarsh.data.model.local.mapper

import info.tommarsh.data.model.MockProvider.category
import info.tommarsh.data.model.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class CategoryDomainToDataMapperTest {

    private val mapper = CategoryDomainToDataMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(category)

        val actual = mapper.map(listOf(categoryModel))

        assertEquals(expected, actual)
    }
}
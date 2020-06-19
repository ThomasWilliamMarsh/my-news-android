package info.tommarsh.mynews.core.category.data.local.model.mapper

import info.tommarsh.mynews.core.MockProvider.category
import info.tommarsh.mynews.core.MockProvider.categoryModel
import junit.framework.Assert.assertEquals
import org.junit.Test

class CategoryDomainToDataMapperTest {

    private val mapper =
        CategoryDomainToDataMapper()

    @Test
    fun `Map to domain layer`() {
        val expected = listOf(category)

        val actual = mapper.map(listOf(categoryModel))

        assertEquals(expected, actual)
    }
}
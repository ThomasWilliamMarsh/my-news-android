package info.tommarsh.data.source.local.category

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import info.tommarsh.data.model.MockProvider.category
import info.tommarsh.data.model.MockProvider.categoryModel
import info.tommarsh.data.model.local.Category
import info.tommarsh.data.model.local.mapper.CategoryDataToDomainMapper
import info.tommarsh.data.model.local.mapper.CategoryDomainToDataMapper
import org.junit.Test

class CategoryLocalDataStoreTest {
    private val dao = mock<CategoryDao>()
    private val dataMapper = mock<CategoryDataToDomainMapper> {
        on { map(category) }.thenReturn(categoryModel)
    }
    private val domainMapper = mock<CategoryDomainToDataMapper> {
        on { map(categoryModel) }.thenReturn(category)
    }
    private val categoryLiveData = MutableLiveData<List<Category>>()

    private val local = CategoryLocalDataStore(dao, dataMapper, domainMapper)

    @Test
    fun `Get categories from DB`() {
        whenever(dao.getCategories()).thenReturn(categoryLiveData)

        local.getCategories()

        verify(dao).getCategories()
    }

    @Test
    fun `Get selected categories from DB`() {
        whenever(dao.getSelectedCategories()).thenReturn(categoryLiveData)

        local.getSelectedCategories()

        verify(dao).getSelectedCategories()
    }

    @Test
    fun `Update category`() {

        local.updateCategory(categoryModel)

        verify(dao).updateCategory(category)
    }
}
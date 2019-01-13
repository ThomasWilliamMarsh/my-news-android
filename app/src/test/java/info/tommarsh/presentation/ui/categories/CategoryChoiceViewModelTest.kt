package info.tommarsh.presentation.ui.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import info.tommarsh.domain.source.CategoryRepository
import info.tommarsh.presentation.model.MockModelProvider.categoryModel
import info.tommarsh.presentation.model.MockModelProvider.categoryViewModel
import info.tommarsh.presentation.model.mapper.CategoryDomainToViewModelMapper
import info.tommarsh.presentation.model.mapper.CategoryViewModelToDomainMapper
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CategoryChoiceViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val categoryRepository = mock<CategoryRepository>()
    private val domainMapper = mock<CategoryViewModelToDomainMapper> {
        on { map(categoryViewModel) }.thenReturn(categoryModel)
    }
    private val viewModelMapper = mock<CategoryDomainToViewModelMapper> {
        on { map(categoryModel) }.thenReturn(categoryViewModel)
    }
    private val categoryChoiceViewModel = CategoryChoiceViewModel(categoryRepository, viewModelMapper, domainMapper)

    @Test
    fun `Get Categories`() {

        categoryChoiceViewModel.getCategories()

        verify(categoryRepository).getCategories()
    }

    @Test
    fun `Update Category`() = runBlocking {

        runBlocking { categoryChoiceViewModel.updateCategory(categoryViewModel) }

        verify(domainMapper).map(categoryViewModel)
        verify(categoryRepository).updateCategory(categoryModel)
    }
}
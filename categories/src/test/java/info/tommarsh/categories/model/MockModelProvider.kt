package info.tommarsh.categories.model

import info.tommarsh.categories.model.CategoryViewModel
import info.tommarsh.core.model.CategoryModel

object MockModelProvider {

    //region category
    val categoryModel = CategoryModel("id", "name", false)
    val categoryViewModel = CategoryViewModel("id", "name", false)
    //endregion
}
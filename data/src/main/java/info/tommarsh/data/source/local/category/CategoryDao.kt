package info.tommarsh.data.source.local.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.tommarsh.data.model.local.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(vararg categories: Category)

    @Query("SELECT * FROM CATEGORY_TABLE")
    fun getCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE selected = :selected")
    fun getSelectedCategoriesStream(selected: Boolean = true): LiveData<List<Category>>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE selected = :selected")
    suspend fun getSelectedCategories(selected: Boolean = true): List<Category>

    @Query("UPDATE CATEGORY_TABLE SET selected = :selected where id = :id")
    suspend fun updateCategory(id: String, selected: Boolean)
}
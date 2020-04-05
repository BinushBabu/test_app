package com.example.testbase.data

import androidx.room.*
import io.reactivex.Flowable


interface DisplayObj {
    fun getLabel(): String
    fun getItemId(): String
}

@Entity(tableName = "personal_data")
data class PersonalData(
    val first_name: String,
    val last_name: String,
    @PrimaryKey
    val email: String,
    val mobile: String,
    val country_code: String,
    val email_verified: String?,
    val mobile_verified: String?,
    val gender: String?,
    val dob: String?,
    val home_town: String?,
    val profile_image: String?,
    val interests: List<SuggestionItem>
)

@Dao
interface PersonalDao {

    @Query("SELECT * FROM personal_data LIMIT 1")
    fun getUser(): Flowable<PersonalData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: PersonalData)

    @Query("DELETE FROM personal_data")
    fun nukeTable()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: PersonalData)
}

data class SuggestionItem(val id: String, val name: String) : DisplayObj {
    override fun getItemId(): String {
        return id
    }

    override fun getLabel(): String {
        return name
    }

    override fun toString(): String {
        return name
    }
}

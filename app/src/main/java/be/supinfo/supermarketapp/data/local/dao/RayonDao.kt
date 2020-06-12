package be.supinfo.supermarketapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.supinfo.supermarketapp.data.remote.Rayon

@Dao
interface RayonDao {

    @Query("SELECT * FROM Rayons")
    suspend fun getAllRayons(): List<Rayon>

    @Insert
    suspend fun insertRayon(rayon: Rayon)

    @Insert
    suspend fun insertRayons(rayons: List<Rayon>)

    @Query("DELETE FROM Rayons")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM Rayons")
    suspend fun getCount(): Int

}
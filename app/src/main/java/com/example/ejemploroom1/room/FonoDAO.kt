package com.example.ejemploroom1.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FonoDAO {

    @Insert
    fun insertar(fono:Fono)

    @Query("Select * from fono")
    fun obtenerTodosFonos(): List<Fono>

    @Query("Select * from fono Where id_persona =:id")
    fun obtenerFonosPersona(id:Int): List<Fono>
}
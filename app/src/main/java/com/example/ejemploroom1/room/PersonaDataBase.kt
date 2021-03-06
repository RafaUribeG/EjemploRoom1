package com.example.ejemploroom1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Persona::class, Fono::class],
    version = 1,
    exportSchema = true
)
abstract class PersonaDataBase:RoomDatabase() {

    abstract val personaDAO:PersonaDAO
    abstract val fonoDao: FonoDAO

    companion object{
        const val DATABASE_NAME = "db-persona"
    }

}
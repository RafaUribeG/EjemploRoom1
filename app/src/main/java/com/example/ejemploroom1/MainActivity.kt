package com.example.ejemploroom1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.room.Room
import com.example.ejemploroom1.room.Fono
import com.example.ejemploroom1.room.Persona
import com.example.ejemploroom1.room.PersonaDataBase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    private lateinit var database : PersonaDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialogo = Dialogo(this)

        database = Room.databaseBuilder(
            application, PersonaDataBase::class.java, PersonaDataBase.DATABASE_NAME
        ).allowMainThreadQueries().build()


        var progressBar:ProgressBar = findViewById(R.id.progressBar)
        var txtRut: EditText = findViewById(R.id.txtRut)
        var txtNombre: EditText = findViewById(R.id.txtNombre)
        var btnGuardar:Button = findViewById(R.id.btnGuardar)
        var txtEdad: EditText = findViewById(R.id.txtEdad)
        var btnMostrarRegistro:Button = findViewById(R.id.btnGuardar)
        var txtResultado: TextView = findViewById(R.id.txtResultados)


        btnGuardar.setOnClickListener {
            var rut:String = txtRut.text.toString()
            var nombre: String = txtNombre.text.toString()
            var edad:Int = txtEdad.text.toString().toInt()

           // progressBar.visibility = View.VISIBLE
            dialogo.mostrarCargando()
           launch {
                var x = withContext(Dispatchers.IO){
                    guardarPersona(rut,nombre, edad)
                }
                if (x > 0){

                    Toast.makeText(applicationContext, "Insertado ok!",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext, "No se pudo registrar",Toast.LENGTH_SHORT).show()
                }
               // progressBar.visibility = View.GONE
                dialogo.ocultarCargando()
            }
        }

        btnMostrarRegistro.setOnClickListener {
            dialogo.mostrarCargando()
            txtResultado.text = "Resultados: \n\n"
            launch {
                var lista = withContext(Dispatchers.IO){
                    mostrarPersonas()
                }
                if(lista.size > 0){
                    for (i in lista){
                        txtResultado.append("id: ${i.id} rut: ${i.rut} nombre:${i.nombre} edad: ${i.edad} \n")
                    }
                }
                else{
                    txtResultado.append("Sin Resultados...")
                }
            }
            dialogo.ocultarCargando()
        }

        database.fonoDao.insertar(Fono(0, 133,1))
        database.fonoDao.insertar(Fono(0, 134,1))
        database.fonoDao.insertar(Fono(0, 135,1))
    }

     fun mostrarPersonas():List<Persona>{
        return database.personaDAO.obtenerListaPersonas()
    }

    suspend fun guardarPersona(rut:String, nombre:String, edad: Int): Long{
        delay(4000)
        var p1 = Persona(0, rut, nombre, edad)
        return database.personaDAO.insertar(p1)
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
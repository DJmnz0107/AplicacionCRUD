package diego.jimenez.aplicacioncrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class activity_registrarDatos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_datos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNum = findViewById<EditText>(R.id.txtNum)
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtEstado = findViewById<EditText>(R.id.txtEstado)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnVerRegistro = findViewById<Button>(R.id.btnVerRegistro)

        fun limpiarCampos() {
            txtNum.text.clear()
            txtTitulo.text.clear()
            txtDescripcion.text.clear()
            txtAutor.text.clear()
            txtEmail.text.clear()
            txtEstado.text.clear()
        }

        btnRegistrar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()

                val addTicket = objConexion?.prepareStatement("INSERT INTO tickets (uuid, numero_ticket, titulo_ticket, descripcion_ticket, autor_ticket, email_autor, estado_ticket) VALUES (?, ?, ?, ?, ?, ?, ?)")!!

                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtNum.text.toString())
                addTicket.setString(3, txtTitulo.text.toString())
                addTicket.setString(4, txtDescripcion.text.toString())
                addTicket.setString(5, txtAutor.text.toString())
                addTicket.setString(6, txtEmail.text.toString())
                addTicket.setString(7, txtEstado.text.toString())

                addTicket.executeUpdate()
                limpiarCampos()
            }


        }

        btnVerRegistro.setOnClickListener {
            val pantallaRegistro = Intent(this, activity_data::class.java)
            startActivity(pantallaRegistro)
        }

        val imgCerrarSesion = findViewById<ImageView>(R.id.imgCerrarSesion)

        imgCerrarSesion.setOnClickListener {

            val context = this

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Cerrar sesión")
            builder.setMessage("¿Desea cerrar sesión en la aplicación?")

            builder.setPositiveButton("Si") { dialog, which ->

                val pantallaLogin = Intent(this, MainActivity::class.java)
                startActivity(pantallaLogin)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()

            dialog.show()
        }







    }
}
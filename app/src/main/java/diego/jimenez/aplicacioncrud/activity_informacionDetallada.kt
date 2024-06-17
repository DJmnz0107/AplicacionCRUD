package diego.jimenez.aplicacioncrud

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_informacionDetallada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_informacion_detallada)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tituloRecibido = intent.getStringExtra("titulo")
        val numeroRecibido = intent.getStringExtra("numTicket")
        val descripcionRecibida = intent.getStringExtra("descripcion")
        val autorRecibido = intent.getStringExtra("autor")
        val emailRecibido = intent.getStringExtra("email")
        val estadoRecibido = intent.getStringExtra("estado")

        val lblTitulo = findViewById<TextView>(R.id.lblTitulo)
        val lblNumero = findViewById<TextView>(R.id.lblNumero)
        val lblDescripcion = findViewById<TextView>(R.id.lblDescripcion)
        val lblAutor = findViewById<TextView>(R.id.lblAutor)
        val lblEmail = findViewById<TextView>(R.id.lblEmail)
        val lblEstado = findViewById<TextView>(R.id.lblEstado)

        lblTitulo.text = tituloRecibido
        lblNumero.text = numeroRecibido
        lblDescripcion.text = descripcionRecibida
        lblAutor.text = autorRecibido
        lblEmail.text = emailRecibido
        lblEstado.text = estadoRecibido

        val imgVolverAtras = findViewById<ImageView>(R.id.imgVolverAtras)
        imgVolverAtras.setOnClickListener {
            val volver = Intent(this, activity_data::class.java)
            startActivity(volver)
        }


    }
}
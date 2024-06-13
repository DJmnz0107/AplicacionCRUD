package diego.jimenez.aplicacioncrud

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTicket

class activity_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager = LinearLayoutManager(this)

        fun mostrarDatos(): List<dataClassTicket> {

            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()

            val resultSet = statement?.executeQuery("SELECT * FROM tickets")!!

            val tickets = mutableListOf<dataClassTicket>()

            while(resultSet.next()) {
                val uuid = resultSet.getString("uuid")
                val numTicket = resultSet.getString("numero_ticket")
                val titulo = resultSet.getString("titulo_ticket")
                val descripcion = resultSet.getString("descripcion_ticket")
                val autor = resultSet.getString("autor_ticket")
                val email = resultSet.getString("email_autor")
                val estado = resultSet.getString("estado_ticket")

                val ticket = dataClassTicket(uuid, numTicket, titulo, descripcion, autor, email, estado)

                tickets.add(ticket)

            }

            return tickets
        }

        CoroutineScope(Dispatchers.IO).launch {

            val ticketDB = mostrarDatos()

            withContext(Dispatchers.Main) {
                val miAdaptador = Adaptador(ticketDB)
                rcvTickets.adapter = miAdaptador
            }
        }
    }



}
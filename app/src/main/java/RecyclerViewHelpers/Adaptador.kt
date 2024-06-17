package RecyclerViewHelpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import diego.jimenez.aplicacioncrud.R
import diego.jimenez.aplicacioncrud.activity_informacionDetallada
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTicket
import java.util.UUID

class Adaptador(var Datos: List<dataClassTicket>):RecyclerView.Adapter<ViewHolder>() {

    fun actualizarListado(nuevaLista: List<dataClassTicket>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun actualizarListadoPostEdicion(uuid: String, nuevoEstado: String) {
        val index = Datos.indexOfFirst { it.uuid == uuid }
        Datos[index].estado = nuevoEstado
        notifyDataSetChanged()
        notifyItemRemoved(index)
    }

    fun eliminarDatos(nombreTicket: String, position: Int) {

        val listaDatos = Datos.toMutableList()

        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val deleteTicket =
                objConexion?.prepareStatement("DELETE Tickets WHERE titulo_ticket = ?")!!

            deleteTicket.setString(1, nombreTicket)
            deleteTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)

        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    fun actualizarDatos(nuevoEstado: String, uuid: String) {

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val updateTicket =
                objConexion?.prepareStatement("UPDATE Tickets SET estado_ticket = ? WHERE uuid = ?")!!

            updateTicket.setString(1, nuevoEstado)
            updateTicket.setString(2, uuid)
            updateTicket.executeUpdate()

            withContext(Dispatchers.Main) {
                actualizarListadoPostEdicion(uuid, nuevoEstado)
            }

        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = Datos[position]

        holder.txtTTitulo.text = item.titulo

        holder.txtTEstado.text = "Estado: " + item.estado

        holder.imgEliminar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmar eliminación")
            builder.setMessage("¿Desea eliminar el ticket?")

            builder.setPositiveButton("Si") { dialog, which ->

                eliminarDatos(item.titulo, position)
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()

            dialog.show()






        }
        holder.imgEditar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)

            builder.setTitle("Editar estado del ticket")

            val cuadroTexto = EditText(context)

            cuadroTexto.setHint(item.estado)

            builder.setView(cuadroTexto)

            builder.setPositiveButton("Guardar") { dialog, which ->

                actualizarDatos(cuadroTexto.text.toString(), item.uuid)

            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()

            dialog.show()
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            val pantallaInformacion = Intent(context, activity_informacionDetallada::class.java)

            pantallaInformacion.putExtra("numTicket", item.numeroTicket)
            pantallaInformacion.putExtra("titulo", item.titulo)
            pantallaInformacion.putExtra("descripcion", item.descripcion)
            pantallaInformacion.putExtra("autor", item.autor)
            pantallaInformacion.putExtra("email", item.email)
            pantallaInformacion.putExtra("estado", item.estado)

            context.startActivity(pantallaInformacion)

        }


    }
}
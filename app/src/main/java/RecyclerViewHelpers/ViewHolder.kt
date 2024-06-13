package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import diego.jimenez.aplicacioncrud.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val txtTTitulo: TextView = view.findViewById(R.id.txtTTicket)

    val txtTEstado: TextView = view.findViewById(R.id.txtTEstado)

    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)

    val imgEliminar: ImageView = view.findViewById(R.id.imgEliminar)
}
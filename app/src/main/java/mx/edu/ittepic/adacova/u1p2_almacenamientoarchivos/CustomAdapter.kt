package mx.edu.ittepic.adacova.u1p2_almacenamientoarchivos

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.io.OutputStreamWriter

class CustomAdapter(private val lista: ArrayList<Items>,private val listener:OnItemClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var vector = ArrayList<String>()


  //  val titles = arrayOf("hola","hey","tu","adios")
   // val details = arrayOf("detalles","mini","ou","osu")
  /*  val images = intArrayOf(R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground,
        R.drawable.ic_launcher_foreground)*/

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int ):ViewHolder{
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        val itemactual = lista[position]



        viewHolder.itemTitle.text = itemactual.text1
       // viewHolder.itemDetail.text = details[position]
        viewHolder.itemDetail.text = itemactual.text2
        viewHolder.itemText.text = itemactual.text3
        viewHolder.itemImage.setImageResource(itemactual.imageResource)



       // viewHolder.itemView.setOnClickListener  {}
    }

    override fun getItemCount() = lista.size


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener{

        var itemText : TextView
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init{
            itemText = itemView.findViewById(R.id.text_view1)
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detal)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position:Int)
    }


//    private fun guardarArchivo(){
//        try{
//            val archivo = OutputStreamWriter(openFileOutput("archivo.txt", AppCompatActivity.MODE_PRIVATE))
//            var cadena = binding.nombre.text.toString()+"\n"+ binding.domiciilio.text.toString()+"\n"+binding.sueldo.text.toString()
//
//            archivo.write(cadena)
//            archivo.flush()
//            archivo.close()
//
//            binding.nombre.setText("")
//            binding.domicilio.setText("")
//            binding.sueldo.setText("")
//            AlertDialog.Builder(this).setMessage("Guardado").show()
//        }catch (e:Exception){
//            AlertDialog.Builder(this).setMessage(e.message).show()
//
//        }
//    }
}
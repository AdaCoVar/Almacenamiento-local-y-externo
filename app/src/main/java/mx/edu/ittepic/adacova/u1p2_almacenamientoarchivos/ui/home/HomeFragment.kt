package mx.edu.ittepic.adacova.u1p2_almacenamientoarchivos.ui.home

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.adacova.u1p2_almacenamientoarchivos.CustomAdapter
import mx.edu.ittepic.adacova.u1p2_almacenamientoarchivos.Items
import mx.edu.ittepic.adacova.u1p2_almacenamientoarchivos.R
import mx.edu.ittepic.adacova.u1p2_almacenamientoarchivos.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class HomeFragment : Fragment(), CustomAdapter.OnItemClickListener {

    private val lista = generarLista(0)
   private val adapter = CustomAdapter(lista,this)
    private var _binding: FragmentHomeBinding? = null

    var vector = ArrayList<String>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

     //aqui va
       // val lista = crearLista()
        val recyclerView = binding.recyclerView
       // recyclerView.adapter = CustomAdapter(lista)
     //   val adapter = CustomAdapter(lista,this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

       // recyclerView.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,vector)

        val buttoninsertar = binding.buttoninsertar.setOnClickListener {
            val pedidos = binding.pedidos.text.toString()
            val mesas = binding.mesas.text.toString()
            var indice = 0
            indice = lista.size
         //  var indicemostrado = lista.size+1
            val nuevoitem = Items(
                R.drawable.cheeseburger, "Orden: $indice",  pedidos, mesas
            )
            lista.add(nuevoitem)
            adapter.notifyItemInserted(indice)

            archivo()
            binding.pedidos.setText("")
            binding.mesas.setText("")
          //  val archivo = OutputStreamWriter(openFileOutput("archivo.txt", AppCompatActivity.MODE_PRIVATE))
        }


        /* fun insertar(view: View){
            val indice: Int = Random.nextInt(8)
            val nuevoitem = Items(
                R.drawable.ic_launcher_foreground, "nuevo item en la posicion $indice","Linea dos"
            )
           lista.add(indice,nuevoitem)
           adapter.notifyItemInserted(indice)
        }
        fun borrar(view: View){
            val indice: Int = Random.nextInt(8)
            lista.removeAt(indice)
            adapter.notifyItemRemoved(indice)

        }*/
        val buttonborrar = binding.buttonborrar.setOnClickListener {
            val indice = lista.size
//            lista.removeAt(indice)

            val inputborrar = EditText(requireContext())
            inputborrar.inputType = InputType.TYPE_CLASS_NUMBER
            AlertDialog.Builder(requireContext())
                .setTitle("Atencion")
                .setMessage("Orden que desea borrar")
                .setView(inputborrar)
                .setPositiveButton("Borrar"){d, i->

                    lista.removeAt(inputborrar.text.toString().toInt())
                //    actualizarLista()
                    adapter.notifyItemRemoved(indice)

                    }

                .setNegativeButton("Cancelar"){d,i->d.cancel()}
                .show()

        }




        //esto al final
        return root
    }

//    private fun actualizarLista(){
//        var nueva = lista.size-1
//        (0..nueva).forEach {
//            nueva.add
//           }
//
//    }


    private fun generarLista(size:Int): ArrayList<Items>{
        val list = ArrayList<Items>()
        for(i in 0 until size){
            val drawable = when (i%3){
                0->R.drawable.cheeseburger
                1->R.drawable.cheeseburger
                else->R.drawable.cheeseburger
            }
            val item = Items(drawable, ""," ", "")
            list+=item

        }
       return list
    }
   private fun archivo(){

      /*  val outputStreamWriter = OutputStreamWriter(
            requireContext().openFileOutput("archivo.txt", MODE_PRIVATE)
        )*/
       val archivo2 = OutputStreamWriter(requireActivity().openFileOutput("archivo.txt",0))
       var cadena = binding.pedidos.text.toString()+"\n"+binding.mesas.text.toString()

       archivo2.write(cadena)
       archivo2.flush()
       archivo2.close()

       Toast.makeText(requireContext(), "Archivado",Toast.LENGTH_SHORT).show()
       /*var cadena = binding.pedidos.text.toString()+"\n" + binding.mesas.text.toString()
    outputStreamWriter.write(cadena)
    outputStreamWriter.flush()
    outputStreamWriter.close()
    Toast.makeText(requireContext(), "Archivado",Toast.LENGTH_SHORT).show()*/
         }

    private fun leerArchivo(){
        try{
            val archivo2 = BufferedReader(InputStreamReader(requireActivity().openFileInput("archivo.txt")))
         //   val archivo = BufferedReader(InputStreamReader(requireContext().openFileInput("archivo.txt")))
            var br= BufferedReader(archivo2)
            var linea = br.readLine()
            val cadena = StringBuilder()
            while (linea != null) {
                cadena.append(linea + "\n")
                linea = br.readLine()
            }

            AlertDialog.Builder(requireContext()).setMessage(cadena).show()
            br.close()
            archivo2.close()




        }catch (e:Exception){
            AlertDialog.Builder(requireContext()).setMessage(e.message).show()

        }

    }

            override  fun onItemClick(position: Int) {
           //    val intent = Intent(requireContext(), ::class)
                leerArchivo()
        Toast.makeText(requireContext(),"Has marcado el pedido",Toast.LENGTH_SHORT).show()
        val clickedItem =  lista[position]
        clickedItem.text1 = "Orden $position entregada"
        adapter.notifyItemChanged(position)
    }






    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
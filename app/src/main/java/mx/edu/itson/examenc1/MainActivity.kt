package mx.edu.itson.examenc1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tableLayout: TableLayout
    private lateinit var tvSubtotal: TextView
    private lateinit var tvIVA: TextView
    private lateinit var tvTotal: TextView

    private var subtotal = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular los elementos de la UI
        val etCantidad = findViewById<EditText>(R.id.etCantidad)
        val etProducto = findViewById<EditText>(R.id.etProducto)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        tableLayout = findViewById(R.id.tableLayout)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvIVA = findViewById(R.id.tvIVA)
        tvTotal = findViewById(R.id.tvTotal)

        btnAgregar.setOnClickListener {
            val cantidad = etCantidad.text.toString().toIntOrNull() ?: 0
            val producto = etProducto.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0

            if (cantidad > 0 && producto.isNotEmpty() && precio > 0) {
                agregarFila(cantidad, producto, precio)
                actualizarTotales()

                // Limpiar los campos después de agregar un producto
                etCantidad.text.clear()
                etProducto.text.clear()
                etPrecio.text.clear()
            } else {
                Toast.makeText(this, "Ingresa datos válidos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun agregarFila(cantidad: Int, producto: String, precio: Double) {
        val fila = TableRow(this)

        val tvCantidad = TextView(this)
        tvCantidad.text = cantidad.toString()

        val tvProducto = TextView(this)
        tvProducto.text = producto

        val tvPrecio = TextView(this)
        tvPrecio.text = "$${"%.2f".format(precio)}"

        val total = cantidad * precio
        val tvTotal = TextView(this)
        tvTotal.text = "$${"%.2f".format(total)}"

        // Agregar las celdas a la fila
        fila.addView(tvCantidad)
        fila.addView(tvProducto)
        fila.addView(tvPrecio)
        fila.addView(tvTotal)

        // Agregar la fila a la tabla
        tableLayout.addView(fila)

        // Sumar al subtotal
        subtotal += total
    }

    private fun actualizarTotales() {
        val iva = subtotal * 0.16
        val total = subtotal + iva

        tvSubtotal.text = "Subtotal: $${"%.2f".format(subtotal)}"
        tvIVA.text = "IVA (16%): $${"%.2f".format(iva)}"
        tvTotal.text = "Total: $${"%.2f".format(total)}"
    }
}
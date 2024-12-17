import java.util.*

fun main() {
    val scanner = abrirScanner()

    var continuar_programa = true
    while (continuar_programa) {
        val billetesComprados = mutableListOf<String>()
        var totalPrecio = 0.0
        var contadorBilletes = 0
        var continuarCompra = true


        println("=== Bienvenido a la máquina de venta de billetes ===")

        while (contadorBilletes < 3 && continuarCompra) {
            val tipoBillete = seleccionarTipoBillete(scanner)
            val zonas = seleccionarNumeroZonas(scanner)
            val precio = calcularPrecio(tipoBillete, zonas)

            agregarBillete(billetesComprados, tipoBillete, zonas, precio)
            totalPrecio += precio
            contadorBilletes++

            if (contadorBilletes < 3) {
                continuarCompra = quiereOtroBillete(scanner)
            } else {
                println("\nHa alcanzado el límite de 3 billetes.")
            }
        }

        mostrarResumenBilletes(billetesComprados, totalPrecio)

        val dineroPagado = procesarPago(scanner, totalPrecio)
        imprimirResumen(billetesComprados, totalPrecio, dineroPagado)

    }
}

fun leerNumero(scanner: Scanner): Int {
    val numero = scanner.nextInt()
    return numero
}

fun leerString(scanner: Scanner): String {
    val palabra = scanner.next()
    return palabra
}

fun leerNumeroDouble(scanner: Scanner): Double {
    val numero = scanner.nextDouble()
    return numero
}

fun seleccionarTipoBillete(scanner: Scanner): String {
    println("\nSeleccione el tipo de billete:")
    println("1 - Billete sencillo\n2 - TCasual\n3 - TUsual\n4 - TFamiliar\n5 - TJove")

    var opcion = 0
    while (opcion !in 1..5) {
        print("Ingrese el número del billete: ")
        opcion = leerNumero(scanner)
        if (opcion !in 1..5) {
            println("Opción inválida. Intente de nuevo.")
        }
    }

    val tipos = listOf("Billete sencillo", "TCasual", "TUsual", "TFamiliar", "TJove")
    return tipos[opcion - 1]
}

fun seleccionarNumeroZonas(scanner: Scanner): Int {
    var zonas = 0
    while (zonas !in 1..3) {
        print("Seleccione el número de zonas (1, 2 o 3): ")
        zonas = leerNumero(scanner)
        if (zonas !in 1..3) {
            println("Número de zonas inválido. Intente de nuevo.")
        }
    }
    return zonas
}

fun calcularPrecio(tipoBillete: String, zonas: Int): Double {
    val precios = mapOf(
        "Billete sencillo" to 2.40,
        "TCasual" to 11.35,
        "TUsual" to 40.00,
        "TFamiliar" to 10.00,
        "TJove" to 80.00
    )

    val base = precios[tipoBillete] ?: 0.0
    val factor =
        when (zonas) {
            2 -> 1.3125
            3 -> 1.8443
            else -> 1.0
        }
    val precio_fin_billete = base * factor
    return precio_fin_billete
}

fun agregarBillete(lista: MutableList<String>, tipo: String, zonas: Int, precio: Double) {
    lista.add("Billete: $tipo | Zonas: $zonas | Precio: €${"%.2f".format(precio)}")
    println("Billete agregado: $tipo | Zonas: $zonas | Precio: €${"%.2f".format(precio)}")
}

fun quiereOtroBillete(scanner: Scanner): Boolean {
    var respuesta = ""
    while (respuesta != "s" && respuesta != "n") {
        print("\n¿Desea comprar otro billete? (s/n): ")
        respuesta = leerString(scanner)
        if (respuesta != "s" && respuesta != "n") {
            println("Respuesta inválida. Por favor, ingrese 's' o 'n'.")
        }
    }
    return respuesta == "s"
}

fun mostrarResumenBilletes(billetes: List<String>, total: Double) {
    println("\n==========================================================")
    println("=== Resumen de billetes seleccionados ===")
    billetes.forEach { println(it) }
    println("Total a pagar: €${"%.2f".format(total)}")
    println("==========================================================\n")
}

fun procesarPago(scanner: Scanner, total: Double): Double {
    var pagado = 0.0
    while (pagado <= total) {
        print("Ingrese el dinero para pagar (Total: €${"%.2f".format(total)}): ")
        pagado += leerNumeroDouble(scanner)
        if (pagado <= total) {
            println("Faltan €${"%.2f".format(total - pagado)}.")
        }
    }
    return pagado
}
//почему так случилось что когда вводишь целую сумму и остается 0,77 и ты вводишь 0,77 то оно говорит что осталось 0 но  всеравно запрашевает оплату
fun imprimirResumen(billetes: List<String>, total: Double, pagado: Double) {
    val cambio = pagado - total
    println("\n==========================================================")
    println("=== Resumen de su compra ===")
    billetes.forEach { println(it) }
    println("Total pagado: €${"%.2f".format(pagado)}")
    println("Su cambio: €${"%.2f".format(cambio)}")
    println("\n¡Gracias por su compra!")
    println("=======================================================\n\n")
}

fun abrirScanner(): Scanner {
    val scanner = Scanner(System.`in`).useLocale(Locale.UK)
    return scanner
}



//fun cerrarScanner(scanner: Scanner) {
//    scanner.close()
//}
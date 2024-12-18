package controllers
import java.util.*

/**
 * Función principal de la máquina de venta de billetes.
 * @author Viktoriia
 */
fun main() {
    val scanner = abrirScanner()
    var continuar_programa = true
    while (continuar_programa) {
        val billetesComprados = mutableListOf<String>()
        var totalPrecio = 0.0
        var contadorBilletes = 0
        var continuarCompra = true

        println("=== Bienvenido a la máquina de venta de billetes ===")

        // Proceso de compra de hasta 3 billetes
        while (contadorBilletes < 3 && continuarCompra) {
            val tipoBillete = seleccionarTipoBillete(scanner)
            val zonas = seleccionarNumeroZonas(scanner)
            val precio = calcularPrecio(tipoBillete, zonas)

            // Se agrega el billete a la lista de comprados
            agregarBillete(billetesComprados, tipoBillete, zonas, precio)
            totalPrecio += precio
            contadorBilletes++

            // Pregunta si el usuario desea seguir comprando
            if (contadorBilletes < 3) {
                continuarCompra = quiereOtroBillete(scanner)
            } else {
                println("\nHa alcanzado el límite de 3 billetes.")
            }
        }

        mostrarResumenBilletes(billetesComprados, totalPrecio)

        val dineroPagado = procesarPago(scanner, totalPrecio)
        imprimirResumen(billetesComprados, totalPrecio, dineroPagado)

        // Mostrar el tiquet de la compra
        tiquet(scanner, billetesComprados)
    }
}

/**
 * Lee un número entero desde la entrada del usuario.
 *@author Viktoriia
 * @param scanner El objeto Scanner para leer la entrada del usuario.
 * @return El número entero ingresado por el usuario.
 */
fun leerNumero(scanner: Scanner): Int {
    val numero = scanner.nextInt()
    return numero
}

/**
 * Lee una cadena de texto desde la entrada del usuario.
 * @author Viktoriia
 * @param scanner El objeto Scanner para leer la entrada del usuario.
 * @return La cadena de texto ingresada por el usuario.
 */
fun leerString(scanner: Scanner): String {
    val palabra = scanner.next()
    return palabra
}

/**
 * Lee un número decimal (Double) desde la entrada del usuario.
 * @author Viktoriia
 * @param scanner El objeto Scanner para leer la entrada del usuario.
 * @return El número decimal ingresado por el usuario.
 */
fun leerNumeroDouble(scanner: Scanner): Double {
    val numero = scanner.nextDouble()
    return numero
}

/**
 * Permite seleccionar el tipo de billete que el usuario desea comprar.
 * @author Viktoriia
 *
 * @param scanner El objeto Scanner para leer la selección del usuario.
 * @return El tipo de billete seleccionado por el usuario.
 */
fun seleccionarTipoBillete(scanner: Scanner): String {
    println("\nSeleccione el tipo de billete:")
    println("1 - Billete sencillo\n2 - TCasual\n3 - TUsual\n4 - TFamiliar\n5 - TJove\n6 - Atras")

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

/**
 * Permite seleccionar el número de zonas para el billete.
 * El usuario puede elegir entre 1, 2 o 3 zonas.
 * @author Viktoriia
 * @param scanner El objeto Scanner para leer la entrada del usuario.
 * @return El número de zonas seleccionado por el usuario.
 */
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

/**
 * Calcula el precio final del billete según el tipo y el número de zonas.
 * @author Viktoriia
 * @param tipoBillete El tipo de billete seleccionado por el usuario.
 * @param zonas El número de zonas seleccionado por el usuario.
 * @return El precio final del billete.
 */
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
    val precio_final_billete = base * factor
    return precio_final_billete
}

/**
 * Agrega un billete a la lista de billetes comprados.
 * @author Viktoriia
 * @param lista La lista donde se agregarán los billetes comprados.
 * @param tipo El tipo de billete seleccionado.
 * @param zonas El número de zonas seleccionadas.
 * @param precio El precio final del billete.
 */
fun agregarBillete(lista: MutableList<String>, tipo: String, zonas: Int, precio: Double) {
    lista.add("Billete: $tipo | Zonas: $zonas | Precio: €${"%.2f".format(precio)}")
    println("Billete agregado: $tipo | Zonas: $zonas | Precio: €${"%.2f".format(precio)}")
}

/**
 * Permite al usuario decidir si desea comprar otro billete.
 * @author Viktoriia
 * @param scanner El objeto Scanner para leer la respuesta del usuario.
 * @return `true` si el usuario quiere comprar otro billete, `false` si no.
 */
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

/**
 * Muestra un resumen de los billetes seleccionados y el total a pagar.
 * @author Viktoriia
 * @param billetes La lista de billetes seleccionados.
 * @param total El total a pagar por los billetes.
 */
fun mostrarResumenBilletes(billetes: List<String>, total: Double) {
    println("\n=============== Resumen de billetes seleccionados ===============")
    billetes.forEach { println(it) }
    println("Total a pagar: €${"%.2f".format(total)}")
    println("==========================================================\n")
}

/**
 * Procesa el pago del cliente, validando que las monedas y billetes sean válidos.
 * @author Viktoriia
 * @param scanner El objeto Scanner para leer la cantidad de dinero ingresada por el usuario.
 * @param total El total a pagar por el cliente.
 * @return La cantidad de dinero pagada por el cliente.
 */
fun procesarPago(scanner: Scanner, total: Double): Double {
    val monedasValidas = listOf(0.01, 0.02, 0.05, 0.10, 0.20, 0.50, 1.00, 2.00, 5.00, 10.00, 20.00, 50.00, 100.00, 200.00, 500.00)
    var pagado = 0.0
    while (total - pagado > 0.01) {
        print("Ingrese el dinero para pagar (Falta: €${"%.2f".format(total - pagado)}): ")
        val ingreso = leerNumeroDouble(scanner)
        if (ingreso in monedasValidas) {
            pagado += ingreso
            if (pagado < total) {
                println("Has ingresado €${"%.2f".format(pagado)}.")
            }
        } else {
            println("Moneda o billete inválido. Solo se aceptan: €${monedasValidas.joinToString(", ")}")
        }
    }
    return pagado
}

/**
 * Imprime el resumen de la compra, incluyendo los billetes seleccionados, el total pagado y el cambio.
 * @author Viktoriia
 * @param billetes La lista de billetes seleccionados.
 * @param total El total de la compra.
 * @param pagado El total pagado por el cliente.
 */
fun imprimirResumen(billetes: List<String>, total: Double, pagado: Double) {
    val cambio = pagado - total
    println("\n=============== Resumen de su compra ===============")
    billetes.forEach { println(it) }
    println("Total pagado: €${"%.2f".format(pagado)}")
    println("Su cambio: €${"%.2f".format(Math.abs(cambio))}")
    println("\n¡No olvides recoger el billete y cambio!")
    println("=======================================================\n")
}

/**
 * Imprime un tiquet con los billetes comprados y agradece al cliente.
 * @author Viktoriia
 * @param scanner El objeto Scanner para leer la respuesta del usuario.
 * @param billetes La lista de billetes comprados.
 * @return `true` si el cliente quiere un tiquet, `false` si no.
 */
fun tiquet(scanner: Scanner, billetes: List<String>): Boolean {
    var respuesta = ""
    if (respuesta != "s" && respuesta != "n") {
        print("\n¿Quieres ticket? (s/n): ")
        respuesta = leerString(scanner)
        if (respuesta == "s") {
            println("\n=============== TIQUET ===============")
            billetes.forEach { println(it) }
            println("========================================\n")
            println("¡Recoge tu ticket!")
            println("\n¡Gracias por su compra!\n\n")
        } else if (respuesta == "n") {
            println("\n¡Gracias por su compra!\n\n")
        }
    }
    return respuesta == "s"
}

/**
 * Inicializa y retorna un objeto `Scanner` para leer desde la entrada estándar.
 * @author Viktoriia
 * @return El objeto `Scanner` configurado para leer desde la entrada estándar.
 */
fun abrirScanner(): Scanner {
    val scanner = Scanner(System.`in`).useLocale(Locale.UK)
    return scanner
}

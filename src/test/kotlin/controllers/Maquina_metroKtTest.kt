package controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
 class Maquina_metroKtTest {

@Test
   fun testPrecioBilleteSencilloUnaZona() {
     val tipoBillete = "Billete sencillo"
     val zonas = 1
     val precioEsperado = 2.40

     val precioCalculado = calcularPrecio(tipoBillete, zonas)

     assertEquals(precioEsperado, precioCalculado, "El precio para el billete sencillo con 1 zona debe ser 2.40 €")
}

@Test
   fun testPrecioTCasualDosZonas() {
     val tipoBillete = "TCasual"
     val zonas = 2
     val precioBase = 11.35
     val factorZonas = 1.3125
     val precioEsperado = precioBase * factorZonas

     val precioCalculado = calcularPrecio(tipoBillete, zonas)

     assertEquals(
      precioEsperado,
      precioCalculado,
      "El precio para el billete TCasual con 2 zonas debe ser $precioEsperado €")
}


@Test
   fun testPrecioTJoveTresZonas() {
     val tipoBillete = "TJove"
     val zonas = 3
     val precioBase = 80.00
     val factorZonas = 1.8443
     val precioEsperado = precioBase * factorZonas

     val precioCalculado = calcularPrecio(tipoBillete, zonas)

     assertEquals(
      precioEsperado,
      precioCalculado,
      "El precio para el billete TJove con 3 zonas debe ser $precioEsperado €")
}

 }
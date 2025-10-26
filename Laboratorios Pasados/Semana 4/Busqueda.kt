import java.io.File
/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 *  Punto 2.4. Actividad 3: Disenar un algoritmo de busqueda binaria sobre una matriz NxM con M impar que devuelva
 *  el elemento único en la fila. 
 */

fun main(args: Array <String>) {
    if (args.isEmpty()) {
        println("Uso: ./runBusqueda archivo_entrada")
        return
    }

    val archivoEntrada = args[0]
    val (matriz, target) = parsearArchivo(archivoEntrada)

    // Encontremos la fila que comienza con el target
    val fila = encontrarFilaPorPrimerElemento(matriz, target)

    if (fila.isEmpty()) {
        println("No se encontró la fila que comienza con el elemento $target")
        return
    }

    // Encontrar elemento unico en la fila
    val elementoUnico = encontrarElementoUnicoEnFila(fila)

    println(elementoUnico)
}

fun parsearArchivo(rutaArchivo: String): Pair<List<List<Int>>, Int> {
    val lineas = File(rutaArchivo).readLines()

    // Primera linea n y m
    val (n, m) = lineas[0].split(" ").map { it.toInt() }

    // Matriz
    val matriz = lineas.subList(1, 1 + n).map { linea -> 
        linea.split(" ").map { it.toInt() }
    }

    val target = lineas[1 + n].toInt()

    return Pair(matriz, target)
}

fun encontrarFilaPorPrimerElemento(matriz: List<List<Int>>, target: Int): List<Int> {
// Busqueda binaria para encontrar la fila
    var inicio = 0
    var fin = matriz.size - 1

    while( inicio <= fin) {
        val medio = (inicio + fin) / 2
        val primerElemento = matriz[medio][0]

        when {
            primerElemento == target -> return matriz[medio]
            primerElemento < target -> inicio = medio + 1
            else -> fin = medio - 1 
        }
    }

    return emptyList()
}

fun encontrarElementoUnicoEnFila(fila: List<Int>): Int {
    // Busqueda binaria para encontrar el elemento
    var inicio = 0 
    var fin = fila.size - 1
    
    while (inicio < fin) {
        val medio = (inicio + fin) / 2

        // Verificamos si efectivamente es elemento unico
        val esUnico = when {
            medio == 0 -> fila[medio] != fila[medio + 1]
            medio == fila.size - 1 -> fila[medio] != fila[medio - 1]
            else -> fila[medio] != fila[medio - 1] && fila[medio] != fila[medio + 1]
        }

        if (esUnico) {
            return fila[medio]
        }

        if (medio % 2 == 0) {
            if (fila[medio] == fila[medio + 1]) {
                inicio = medio + 2
            } else {
                fin = medio - 1
            }
        } else {
            if (fila[medio] == fila[medio - 1]) {
                inicio = medio + 1
            } else {
                fin = medio - 1
            }
        }
    }
    return -1 // Es una mousequerramienta que no deberia pasar, pero no esta de mas ponerla por si falla algo.
}

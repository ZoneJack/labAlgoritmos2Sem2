// UnirFilas.kt
import java.io.File
import java.io.FileNotFoundException
import java.util.PriorityQueue

/**
 * Universidad Simon Bolivar

 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 *  Punto 2.3. Actividad 2: Disenar un algoritmo de ordenamiento que recibe una matriz NxM con M impar y devuelva el string con los elementos ordenados
 *  . 
 */


fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Error: Se debe proporcionar el nombre del archivo de entrada correcto.")
        println("Uso: ./runUnirFilas <archivo_entrada>")
        return
    }

    try {
        val inputFile = File(args[0])
        val lines = inputFile.readLines().filter {it.isNotBlank() } //Filtramos lineas vacias, just in case.
        
        if (lines.isEmpty()) {
        	println("Error: Archivo not found.")
        	return
        }

        // Leer dimensiones
        val firstLine = lines[0].split(" ").filter { it.isNotBlank() }
        if (firstLine.size != 2) {
        	println("Error: Error de Formato, se esperan al menos 2 numeros.")
        	return
        }
        
        val n = firstLine[0].toInt()
        val m = firstLine[1].toInt()
        
        // Verifica que hay suficientes lineas
        if (lines.size < 1 + n) {
        	println("Error: El archivo no tiene lineas suficientes. Se espera al menos ${1 + n}, solo hay: ${lines.size}")
        	return
        }
        
        // Leer matriz
        val matrix = lines.subList(1, 1 + n).map { line ->
            line.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        }
	
	for (i in matrix.indices) {
		if (matrix[i].size != m) {
		println("Error: La fila ${i + 1} tiene ${matrix[i].size} elementos, pero se esperan $m")
		return
		}
	}
        // Obtener filas impares (1-based: filas 1, 3, 5, ...)
        val filasImpares = mutableListOf<List<Int>>()
        for (i in 0 until n step 2) {
            filasImpares.add(matrix[i])
        }

	// Si no hay fila impares
        if (filasImpares.isEmpty()) {
            println()
            return
        }


        // Si solo hay una fila impar, retornarla directamente
        if (filasImpares.size == 1) {
            println(filasImpares[0].joinToString(" "))
            return
        }

        // Merge de k-arrays ordenados usando Min-Heap - Este algoritmo tiene O(N log k) tiempo
        val resultado = mergeKSortedArrays(filasImpares)
        
        println(resultado.joinToString(" "))

    } catch (e: NumberFormatException) {
        println("Error: Formato de numero invalido. ${e.message}")
    } catch (e: FileNotFoundException) {
        println("Error: El archivo '${args[0]}' no fue encontrado.")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}

/**
 * Combina multiples arrays ordenados en un solo array ordenado
 * usando un Min-Heap. Complejidad: O(N log k) donde N = total elementos, k = número de arrays
 */
private fun mergeKSortedArrays(arrays: List<List<Int>>): List<Int> {
    val result = mutableListOf<Int>()
    val heap = PriorityQueue<Element>()

    // Inicializar heap con el primer elemento de cada array
    for (i in arrays.indices) {
        if (arrays[i].isNotEmpty()) {
            heap.offer(Element(arrays[i][0], i, 0))
        }
    }

    // Procesar el heap
    while (heap.isNotEmpty()) {
        val current = heap.poll()
        result.add(current.value)
        
        val nextIndex = current.indexInArray + 1
        if (nextIndex < arrays[current.arrayIndex].size) {
            heap.offer(Element(
                arrays[current.arrayIndex][nextIndex],
                current.arrayIndex,
                nextIndex
            ))
        }
    }

    return result
}

/**
 * Clase auxiliar para representar elementos en el heap
 */
private data class Element(
    val value: Int,
    val arrayIndex: Int,
    val indexInArray: Int
) : Comparable<Element> {
    override fun compareTo(other: Element): Int {
        return this.value.compareTo(other.value)
    }
}

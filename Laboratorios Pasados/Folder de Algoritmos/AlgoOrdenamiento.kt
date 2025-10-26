operator fun Number.compareTo(other: Number) = this.toDouble().compareTo(other.toDouble())

/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 *  Actividad 1: Libreria de Algoritmo de Ordenamiento, con la implementacion de BubbleSort, InsertionSort
 *  y Mergesort basado en los pseudocodigos del libro Cormen.
 */


/**
 * Implementacion del algoritmo BubbleSort
 * Ordena el Array in-place con complejidad O(n^2)
 *
 */
fun bubbleSort(A: Array<Number>) {
    for (i in 0 until A.size - 1) {
        for (j in A.size - 1 downTo i + 1) {
            if (A[j] < A[j - 1]) {
                val temp = A[j]
                A[j] = A[j - 1]
                A[j - 1] = temp
            }
        }
    }
}
//------------------------------------------------------------
/**
 * Implementacion del Insertion Sort
 * Complejidad O(n^2) en el peor caso y O(n) en el mejor caso
 * */
fun insertionSort(A: Array<Number>) {
    for (i in 1 until A.size) {
        val key = A[i]
        var j = i - 1
        while (j >= 0 && A[j] > key) {
            A[j + 1] = A[j]
            j--
        }
        A[j + 1] = key
    }
}
//-----------------------------------------------------------------
/**
 * Funcion auxiliar para MergeSort que combina dos Arrays ordenados.
 *
 * */
fun merge(left: Array<Number>, right: Array<Number>): Array<Number> {
    val result = mutableListOf<Number>()
    var i = 0
    var j = 0

    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            result.add(left[i])
            i++
        } else {
            result.add(right[j])
            j++
        }
    }
    // Agregar elementos restantes del Array izquierdo
    while (i < left.size) {
        result.add(left[i])
        i++
    }
    // Agregar elementos restantes del Array derecho
    while (j < right.size) {
        result.add(right[j])
        j++
    }
    return result.toTypedArray()
}

/**
 * Implementacion del Mergesort, en base al libro Cormen. Ordenando el array usando el Divide-and-conquer
 * Complejidad O(N log n )
 */
fun mergesort(arr: Array<Number>) {
    if (arr.size <= 1) return // Caso Base: Un array de tamano 0 o 1

    val mid = arr.size/2
    val left = arr.sliceArray(0 until mid)
    val right = arr.sliceArray(mid until arr.size)

    // Llamadas recursivas para ordenar las mitades
    mergesort(left)
    mergesort(right)
    // Llamamos la funcion auxiliar para ordenar las matrices
    val merged = merge(left, right)
    // Devolver todo
    for (i in merged.indices) {
        arr[i] = merged[i]
    }
}

Aqui poner Heapsort
/**
 * Implementacion del algoritmo HeapSort
 * 
 *
 */
fun heapsort(A: Array<Number>) {
    val n = A.size
    buildMaxHeap(A)
    for (i in n - 1 downTo 1) {
        // Intercambiar el primer elemento (maximo) con el ultimo elemento no ordenado
        val temp = A[0]
        A[0] = A[i]
        A[i] = temp
        // Llamar maxHeapify en el heap reducido
        maxHeapify(A, 0, i)
    }
}

/**
 * Funcion auxiliar para construir un max-heap a partir del array dado.
 */
fun buildMaxHeap(A: Array<Number>) {
    val n = A.size
    // Llama a maxHeapify en todos los nodos que no son hojas (del ultimo padre al primero)
    for (i in (n / 2) - 1 downTo 0) {
        maxHeapify(A, i, n)
    }
}

/**
 * Funcion auxiliar para mantener la propiedad de max-heap
 */
fun maxHeapify(A: Array<Number>, i: Int, heapSize: Int) {
    val left = 2 * i + 1
    val right = 2 * i + 2
    var largest = i
    
    // Comprobar si el hijo izquierdo existe y es mayor que el nodo actual
    if (left < heapSize && A[left] > A[largest]) {
        largest = left
    }
    // Comprobar si el hijo derecho existe y es mayor que el 'largest' hasta ahora
    if (right < heapSize && A[right] > A[largest]) {
        largest = right
    }
    // Si el 'largest' no es el nodo actal, intercambiarlos y seguir heapificando
    if (largest != i) {
        val swap = A[i]
        A[i] = A[largest]
        A[largest] = swap
        // Llamada recursiva para asegurar que el sub-arbol siga siendo un max-heap
        maxHeapify(A, largest, heapSize)
    }
}

//-------------------------------------------------------------------------------
//-------------------------------------------------------------------------------

// UnirFilas.kt
import java.io.File
import java.io.FileNotFoundException

/**
 * Este programa lee una matriz de un archivo, extrae las filas impares
 * y las combina en un único arreglo ordenado en tiempo lineal.
 */
fun main(args: Array<String>) {
    // 1. Validar que se proporcionó un nombre de archivo.
    if (args.isEmpty()) {
        println("Error: Debe proporcionar el nombre del archivo de entrada.")
        println("Uso: ./runUnirFilas <archivo_entrada>")
        return
    }

    try {
        val inputFile = File(args[0])
        val lines = inputFile.readLines()

        // 2. Leer dimensiones y la matriz del archivo.
        val dimensions = lines[0].split(" ").map { it.toInt() }
        val n = dimensions[0]
        val matrix = lines.drop(1).map { line ->
            line.split(" ").map { it.toInt() }.toTypedArray()
        }

        // 3. Si no hay filas, no hay nada que hacer.
        if (n == 0) {
            println()
            return
        }

        // 4. Iniciar el resultado con la primera fila (fila impar #1).
        var resultArray = matrix[0]

        // 5. Iterar sobre las demás filas impares y mezclarlas con el resultado.
        // El bucle empieza en 2 (la tercera fila) y avanza de 2 en 2.
        for (i in 2 until n step 2) {
            resultArray = merge(resultArray, matrix[i])
        }

        // 6. Imprimir el resultado final.
        println(resultArray.joinToString(" "))

    } catch (e: FileNotFoundException) {
        println("Error: El archivo '${args[0]}' no fue encontrado.")
    } catch (e: Exception) {
        println("Error: El formato del archivo es inválido. ${e.message}")
    }
}

/**
 * Combina dos arreglos ordenados en un nuevo arreglo ordenado.
 * Esta operación se ejecuta en tiempo lineal O(n + m).
 */
private fun merge(arr1: Array<Int>, arr2: Array<Int>): Array<Int> {
    val result = Array(arr1.size + arr2.size) { 0 }
    var i = 0 // Puntero para arr1
    var j = 0 // Puntero para arr2
    var k = 0 // Puntero para result

    while (i < arr1.size && j < arr2.size) {
        if (arr1[i] <= arr2[j]) {
            result[k++] = arr1[i++]
        } else {
            result[k++] = arr2[j++]
        }
    }

    // Copiar los elementos restantes de arr1, si los hay
    while (i < arr1.size) {
        result[k++] = arr1[i++]
    }

    // Copiar los elementos restantes de arr2, si los hay
    while (j < arr2.size) {
        result[k++] = arr2[j++]
    }

    return result
}

//-----------------------------------------------
//Makefile
# Nombre del archivo JAR que se creará
JAR_NAME=UnirFilas.jar

# Compilador de Kotlin
KOTLINC=kotlinc

# Regla por defecto: compilar el proyecto
default: compile

# Regla para compilar el archivo .kt en un .jar ejecutable
compile:
	$(KOTLINC) UnirFilas.kt -include-runtime -d $(JAR_NAME)

# Regla para limpiar los archivos compilados
clean:
	rm -f $(JAR_NAME)

//------------------------------
//runUnirFilas.sh
#!/bin/bash

# Ejecuta el archivo JAR y le pasa el primer argumento ($1) que reciba el script.
# $1 será el nombre del archivo de la matriz.
java -jar UnirFilas.jar "$1"

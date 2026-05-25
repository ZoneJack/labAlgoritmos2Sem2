import java.io.File
import java.io.IOException

/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * EXAMEN 1 
 * Estudiante:
 * 	      Jose Daniel Torbet, Carnet: 21-10650.
 */
/**
 * Examen 2 - Problema 2
 * Debemos contar cuantas veces aparece cada pais y luego devolver esos conteos ordenados alfabeticamente.
 * Tiempo Objetivo O(nLogn)
 */
 
 // Funcion principal
 
 fun main(args: Array<String>) {
    
    if (args.isEmpty()) {
        println("!!Error!! Debe ingresar un archivo de entrada")
        println("Uso: ./runRegistroBellezas.sh <archivo_entrada>")
        return
    }

    val filename = args[0]
    val file = File(filename)

    if (!file.exists()) {
        println("!!Error!! el archivo '$filename' no existe. Intentelo de nuevo")
        return
    }

    val n: Int
    val paises: Array<String>

    try {
        val reader = file.bufferedReader()
        
        n = reader.readLine()?.toIntOrNull() ?: 0

        if (n <= 0) {
            reader.close()
            return 
        }


        paises = Array(n) { "" }
        
        for (i in 0 until n) {
            val line = reader.readLine()
            if (line != null && line.isNotEmpty()) {
                val parts = line.split(" ", limit = 2) 
                paises[i] = parts[0]
            }
        }
        reader.close()
        
    } catch (e: IOException) {
        println("!!Error!! No se pudo leer el archivo. ${e.message}")
        return
    } catch (e: NumberFormatException) {
        println("!!Error!! La primera linea del archivo no es un numero entero valido.")
        return
    }
 	
 	// --Redes de seguridad listas--
    mergeSort(paises, 0, n - 1)
    
    var currentCountry = paises[0]
    var count = 1

    for (i in 1 until n) {
        if (paises[i] == currentCountry) {
            count++
        } else {
            println("$currentCountry $count")
            
            currentCountry = paises[i]
            count = 1
        }
    }
    
    println("$currentCountry $count")
}
 // Programa Finaliza
 // Iniciamos Mergesort (Para asegurar ese O(nLogn)
 
fun mergeSort(arr: Array<String>, left: Int, rigth: Int) {
    if (left < rigth) {
        val mid = (left + rigth) / 2
        
        mergeSort(arr, left, mid)
        mergeSort(arr, mid + 1, rigth)
        
        merge(arr, left, mid, rigth)
    }
}
 
fun merge(arr: Array<String>, left: Int, mid: Int, rigth: Int) {

    val n1 = mid - left + 1
    val n2 = rigth - mid

    val L = Array(n1) { arr[left + it] }
    val R = Array(n2) { arr[mid + 1 + it] }


    var i = 0 
    var j = 0 
    var k = left 

    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i]
            i++
        } else {
            arr[k] = R[j]
            j++
        }
        k++
    }

    while (i < n1) {
        arr[k] = L[i]
        i++
        k++
    }

    while (j < n2) {
        arr[k] = R[j]
        j++
        k++
    }
}
 // Fin del Ejercicio

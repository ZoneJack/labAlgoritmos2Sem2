/**

 * Universidad Simon Bolivar

 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * EXAMEN 2
 * Estudiante:
 * 	      Jose Daniel Torbet, Carnet: 21-10650.
 */


/**
 * Examen 2 - Problema 1

 * Se reciben un array:Entreros y por cada A[i] se cuenta los x<A[i] y se devuelve j[i]=sum(x<A[i]) en 
 * un array.
 */

data class Item(val value: Int, val originalIndex: Int)
 
 // Funcion Principal
fun main() {
    val dynamicList = mutableListOf<Int>()
    
    val line = readLine()
    
    if (line != null) {
        val stringNumbers = line.split(' ')
        for (s in stringNumbers) {
            if (s.isNotEmpty()) {
                try {
                    dynamicList.add(s.toInt())
                } catch (e: NumberFormatException) {
                }
            }
        }
    }

    val n = dynamicList.size
    
    val items = Array(n) { i -> Item(dynamicList[i], i) }
    
    val counts = Array(n) { 0 }

    mergeSortAndCount(items, counts, 0, n - 1)

    println(counts.joinToString(" "))
}
// Finalizamos Programa 
// Iniciamos Mergesort modificado para que cuente tambien
fun mergeSortAndCount(items: Array<Item>, counts: Array<Int>, left: Int, rigth: Int) {
    if (left < rigth) {
        val mid = (left + rigth) / 2
        
        mergeSortAndCount(items, counts, left, mid)
        mergeSortAndCount(items, counts, mid + 1, rigth)
        
        merge(items, counts, left, mid, rigth)
    }
}


fun merge(items: Array<Item>, counts: Array<Int>, left: Int, mid: Int, rigth: Int) {
    
    val temp = Array(rigth - left + 1) { Item(0, 0) }
    
    var i = left        
    var j = mid + 1     
    var k = 0           
    
    var rigthSmallerCount = 0

    while (i <= mid && j <= rigth) {
        if (items[i].value > items[j].value) {

            rigthSmallerCount++
            
            temp[k++] = items[j++]
            
        } else {

            counts[items[i].originalIndex] += rigthSmallerCount
            
            temp[k++] = items[i++]
        }
    }

    while (i <= mid) {
        counts[items[i].originalIndex] += rigthSmallerCount
        temp[k++] = items[i++]
    }
    
    while (j <= rigth) {
        temp[k++] = items[j++]
    }

    for (idx in 0 until temp.size) {
        items[left + idx] = temp[idx]
    }
}

 // Fin del Ejercicio

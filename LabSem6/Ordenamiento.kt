operator fun Number.compareTo(other: Number) = this.toDouble().compareTo(other.toDouble())

/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 * Libreria de Ordenamiento
 */

//============================================================
/**
 * Semana 2 - Laboratorio 1
 * Actividad: Implementar los algoritmos de ordenamiento:
 * Bubblesort, Insertionsort y Mergesort.
 */

//------------------------Bubble Sort------------------------
/**
 * Implementacion del algoritmo BubbleSort
 * Ordena el Array in-place con complejidad O(n^2)
 */
fun bubblesort(A: Array<Number>) {
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

//-----------------------Insertion Sort-----------------------

/**
 * Implementacion del Insertion Sort
 * Complejidad O(n^2) en el peor caso y O(n) en el mejor caso
 */
fun insertionsort(A: Array<Number>) {
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

//-------------------------Merge Sort-------------------------

/**
 * Funcion auxiliar para MergeSort que combina dos Arrays ordenados.
 */
fun merge(A: Array<Number>, left: Array<Number>, right: Array<Number>) {
    var i = 0
    var j = 0
    var k = 0

    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            A[k] = left[i]
            i++
        } else {
            A[k] = right[j]
            j++
        }
        k++
    }

    while (i < left.size) {
        A[k] = left[i]
        i++
        k++
    }

    while (j < right.size) {
        A[k] = right[j]
        j++
        k++
    }
}

/**
 * Implementacion del Mergesort. Ordenando el array usando el Divide-and-conquer
 * Complejidad O(N log n )
 */
fun mergeSort(A: Array<Number>) {
    if (A.size > 1) {
        val mid = A.size / 2
        val left =Array<Number>(mid) { 0 }
        val right = Array<Number>(A.size - mid) { 0 }

        for (i in 0 until mid) {
            left[i] = A[i]
        }
        for (i in mid until A.size) {
            right[i - mid] = A[i]
        }

        mergeSort(left)
        mergeSort(right)
        merge(A, left, right)
    }
}

//============================================================
/**
 * Semana 3 - Laboratorio 2 
 * Actividad: Anadir Heapsort a la libreria de Algoritmo de Ordenamiento.
 */

 /**
 * Funciones auxiliares para el algoritmo Heapsort.
 * buildMaxHeap: Construye el Array en base a maxHeapify.
 * maxHeapify: El procedimiento se encarga que si hay un nodo mas pequeno que
 * su hijo, lo mueve intercambiando la clave y lo repite hasta que
 * que se cumpla que Parent(x)> x.
 */

fun maxHeapify(A: Array<Number>, i: Int, heapSize: Int) {
    val left = 2 * i + 1
    val right = 2 * i + 2
    var largest = i
    
    if (left < heapSize && A[left] > A[largest]) {
        largest = left
    }
    if (right < heapSize && A[right] > A[largest]) {
        largest = right
    }
    if (largest != i) {
        val swap = A[i]
        A[i] = A[largest]
        A[largest] = swap
        maxHeapify(A, largest, heapSize)
    }
}

fun buildMaxHeap(A: Array<Number>) {
    val n = A.size
    for (i in (n / 2) - 1 downTo 0) {
        maxHeapify(A, i, n)
    }
}

/**
 * Funcion principal del heapsort, que funciona a base de buildMaxHeap y maxHeapify.
 */

 fun heapsort(A: Array<Number>) {
    val n = A.size
    buildMaxHeap(A)
    for (i in n - 1 downTo 1) {
        val temp = A[0]
        A[0] = A[i]
        A[i] = temp
        maxHeapify(A, 0, i)
    }
}

//============================================================
/**
 * Semana 5 - Laboratorio 3
 * Actividad: Añadir dos variantes del algoritmo Quicksort 
 * a la libreria de Algoritmo de Ordenamiento:
 * Quicksort clasico y Quicksort with 3-way partitioning
 */

/**
 * Función auxiliar para intercambiar dos elementos en un arreglo.
 * Corresponde al 'exch' mencionado en el enunciado.
 */
fun swap(A: Array<Number>, i: Int, j: Int) {
    val temp = A[i]
    A[i] = A[j]
    A[j] = temp
}

//-----------------------QuickSort Clasico-----------------------
/**
 * Función auxiliar para particionar el arreglo alrededor de un pivote.
 */
fun partition(A: Array<Number>, p: Int, r: Int): Int {
    val x = A[r] // Pivote
    var i = p - 1
    for (j in p until r) {
        if (A[j] <= x) {
            i++
            swap(A, i, j)
        }
    }
    swap(A, i + 1, r)
    return i + 1
}

/**
 * Implementación del algoritmo Quicksort.
 */
fun quicksortClasico(A: Array<Number>, p: Int = 0, r: Int = A.size - 1) {
    if (p < r) {
        val q = partition(A, p, r)
        quicksortClasico(A, p, q - 1)
        quicksortClasico(A, q + 1, r)
    }
}

//-------------------QuickSort 3-Way Partitioning-------------------
/**
 * Implementación del algoritmo Quicksort con particionamiento de 3 vías.
 */
fun quicksortThreeway(A: Array<Number>, l: Int = 0, r: Int = A.size - 1) {
    if (r <= l) return
    var i = l - 1
    var j = r
    var p = l - 1
    var q = r
    val v = A[r] // Pivote

    while (true) {
        while (A[++i] < v) {}
        while (v < A[--j]) {
            if (j == l) break
        }
        
        if (i >= j) break
        
        swap(A, i, j)
        
        if (A[i] == v) {
            p++
            swap(A, p, i)
        }
        if (A[j] == v) {
            q--
            swap(A, j, q)
        }       
    }

    swap(A, i, r)

    j = i - 1
    i = i + 1

    for (k in l..p-1) {
        swap(A, k, j)
        j--
    }

    for (k in (r - 1) downTo q + 1) {
        swap(A, k, i)
        i++
    }
    quicksortThreeway(A, l, j)
    quicksortThreeway(A, i, r)
}
//============================================================
/**
 * Semana 6 - Laboratorio 4
 * Actividad: Añadir Counting Sort y Radix Sort a la librería 
 * de Algoritmos de Ordenamiento.
 */

//-----------------------Counting Sort-----------------------

/**
 * Implementación del algoritmo Counting Sort
 * Complejidad: O(n + k) donde n es el tamaño del arreglo y k el rango de valores
 */
 
fun countingSort(A: IntArray, maxValue: Int) {
    if (A.isEmpty()) return
    
    val n = A.size
    val count = IntArray(maxValue + 1) { 0 }
    val output = IntArray(n)
    
    // Contar frecuencia de cada elemento
    for (i in 0 until n) {
        count[A[i]]++
    }
    
    // Calcular posiciones acumuladas
    for (i in 1..maxValue) {
        count[i] += count[i - 1]
    }
    
    // Colocar elementos en posición ordenada
    for (i in n - 1 downTo 0) {
        output[count[A[i]] - 1] = A[i]
        count[A[i]]--
    }
    
    // Copiar de vuelta al arreglo original
    for (i in 0 until n) {
        A[i] = output[i]
    }
}

//------------------------Radix Sort-------------------------

/**
 * Funcion auxiliar para obtener el digito en una posicion especifica
 */
private fun getDigit(number: Int, digitPosition: Int): Int {
    var divisor = 1
    repeat(digitPosition - 1) { divisor *= 10 }
    return (number / divisor) % 10
}

/**
 * Implementacion de Counting Sort modificada para Radix Sort
 */
private fun countingRadixSort(A: Array<Int>, digitPosition: Int) {
    val n = A.size
    val k = 9  // 
    val B = Array(n) { 0 }
    val C = Array(k + 1) { 0 }
    
    for (j in A.indices) {
        val digit = getDigit(A[j], digitPosition)
        C[digit] = C[digit] + 1
    }
    
    for (i in 1..k) {
        C[i] = C[i] + C[i - 1]
    }
    
    for (j in n - 1 downTo 0) {
        val digit = getDigit(A[j], digitPosition)
        B[C[digit] - 1] = A[j]  // Ajustar índice
        C[digit] = C[digit] - 1
    }
    
    for (i in A.indices) {
        A[i] = B[i]
    }
}

/**
 * Implementación del algoritmo Radix Sort
 * Complejidad: O(d * (n + k)) donde d es el número de dígitos
 */
fun radixSort(A: Array<Number>) {
    val intArray = A.map { it.toInt() }.toTypedArray()
    
    if (intArray.isEmpty()) return
    
    val maxNum = intArray.maxOrNull() ?: 0
    val d = if (maxNum == 0) 1 else (Math.log10(maxNum.toDouble()) + 1).toInt()
    
    for (i in 1..d) {
        countingRadixSort(intArray, i)
    }
    
    for (i in A.indices) {
        A[i] = intArray[i]
    }
}
//============================================================
// Aqui acabamos la primera mitad del Laboratorio.

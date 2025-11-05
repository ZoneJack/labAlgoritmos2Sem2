import kotlin.math.*


/**
 * Prueba de Algoritmos
 * Se encarga de medir el rendimiento de los algoritmos de ordenamiento
 * y generar gráficas de comparacion.
 */

data class Arguments(
    val numIntentos: Int,
    val tipoArreglo: String,
    val algoritmos: Array<String>,
    val tamanos: IntArray,
    val nombreFigura: String?
)

fun parseArguments(args: Array<String>): Arguments? {
    var numIntentos: Int? = null
    var tipoArreglo: String? = null
    var algoritmos: Array<String>? = null
    var tamanos: IntArray? = null
    var nombreFigura: String? = null

    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "-t" -> numIntentos = args.getOrNull(++i)?.toIntOrNull()
            "-s" -> tipoArreglo = args.getOrNull(++i)
            "-a" -> {
                val algString = args.getOrNull(++i)
                algoritmos = algString?.split(",")?.toTypedArray()
            }
            "-n" -> {
                val tamString = args.getOrNull(++i)
                tamanos = tamString?.split(",")?.mapNotNull { it.toIntOrNull() }?.toIntArray()
            }
            "-o" -> nombreFigura = args.getOrNull(++i)
            else -> {
                println("Error: Parámetro '${args[i]}' no reconocido.")
                return null
            }
        }
        i++
    }

    if (numIntentos == null || tipoArreglo == null || algoritmos == null || tamanos == null) {
        println("!!Error!! Faltan parametros obligatorios o son incorrectos.")
        println("Uso: -t <intentos> -s <tipo_arreglo> -a <algoritmos> -n <tamannos> [-o <nombre_figura>]")
        return null
    }

    return Arguments(numIntentos, tipoArreglo, algoritmos, tamanos, nombreFigura)
}

fun generarArreglo(tipoArreglo: String, n: Int): Array<Number>? {
    return when (tipoArreglo.lowercase()) {
        "random" -> Array(n) { (0 until n).random() }
        "sorted" -> Array(n) { it }
        "inv" -> Array(n) { n - it - 1 }
        "zu" -> Array(n) { if (it < n/2) 0 else 1 }
        "media" -> {
            Array(n) { 
                if (it < n/2) it 
                else n - (it - n/2) - 1 
            }
        }
        else -> {
            println("Error: Tipo de arreglo '$tipoArreglo' no reconocido.")
            null
        }
    }
}

class ResultadoAlgoritmo(
    val algoritmo: String,
    val tamano: Int,
    val tiempos: DoubleArray
)

class DatosGrafica(
    val algoritmo: String,
    val tamano: Int,
    val tiempoMin: Double,
    val tiempoProm: Double,
    val tiempoMax: Double
)

fun verificarOrdenado(A: Array<Number>): Boolean {
    for (i in 0 until A.size - 1) {
        if (A[i].toDouble() > A[i + 1].toDouble()) {
            return false
        }
    }
    return true
}

fun calcularPromedio(arr: DoubleArray): Double {
    var suma = 0.0
    for (i in arr.indices) {
        suma += arr[i]
    }
    return suma / arr.size
}

fun calcularMinimo(arr: DoubleArray): Double {
    var min = arr[0]
    for (i in 1 until arr.size) {
        if (arr[i] < min) min = arr[i]
    }
    return min
}

fun calcularMaximo(arr: DoubleArray): Double {
    var max = arr[0]
    for (i in 1 until arr.size) {
        if (arr[i] > max) max = arr[i]
    }
    return max
}

fun calcularDesviacion(arr: DoubleArray, promedio: Double): Double {
    var sumaCuadrados = 0.0
    for (i in arr.indices) {
        sumaCuadrados += (arr[i] - promedio) * (arr[i] - promedio)
    }
    return sqrt(sumaCuadrados / arr.size)
}

// Funcion principal
fun main(args: Array<String>) {
    val parsedArgs = parseArguments(args) ?: return
    val numIntentos = parsedArgs.numIntentos
    val tipoArreglo = parsedArgs.tipoArreglo
    val algoritmos = parsedArgs.algoritmos
    val tamanos = parsedArgs.tamanos
    val nombreFigura = parsedArgs.nombreFigura
    
    val resultadosGrafica = arrayListOf<DatosGrafica>()
    val resultadosPorAlgoritmo = arrayListOf<ResultadoAlgoritmo>()
    
    println("== Prueba de Algoritmo de Ordenamiento ==")
    println("Numero de intentos: $numIntentos")
    println("Tipo de arreglo: $tipoArreglo")
    println("Algoritmos: ${algoritmos.joinToString(", ")}")
    println("tamannos: ${tamanos.joinToString(", ")}")
    if (nombreFigura != null) {
        println("Figura de salida: $nombreFigura.png")
    }
    println()

    // Procesar cada tamaño de arreglo
    for (n in tamanos) {
        println("--- Procesando tamaño: $n ---")
        val arregloOriginal = generarArreglo(tipoArreglo, n) ?: continue

        for (alg in algoritmos) {
            println("  Algoritmo: $alg")
            val tiempos = DoubleArray(numIntentos) { 0.0 }

            for (intento in 0 until numIntentos) {
                val arregloCopia = arregloOriginal.copyOf()
                
                val tiempo = System.nanoTime().let { start ->
                    when (alg.lowercase()) {
                        "is" -> insertionsort(arregloCopia)
                        "hs" -> heapsort(arregloCopia)
                        "ms" -> mergeSort(arregloCopia)
                        "qs" -> quicksortClasico(arregloCopia)
                        "dq" -> quicksortThreeway(arregloCopia)
                        "cs" -> {
                            // Para Counting Sort, necesitamos convertir a enteros
                            val intArray = IntArray(arregloCopia.size) { i -> arregloCopia[i].toInt() }
                            val maxValue = intArray.fold(0) { max, value -> if (value > max) value else max }
                            countingSort(intArray, maxValue)
                            // Convertir de vuelta a Array<Number>
                            for (i in arregloCopia.indices) {
                                arregloCopia[i] = intArray[i]
                            }
                        }
                        "rs" -> radixSort(arregloCopia)
                        else -> {
                            println("    Error: Algoritmo '$alg' no reconocido.")
                            continue
                        }
                    }
                    (System.nanoTime() - start) / 1_000_000.0 // Convertir a milisegundos
                }
                
                tiempos[intento] = tiempo
                
                // Necesitamos verificar que el arreglo esta ordenado
                if (!verificarOrdenado(arregloCopia)) {
                    println("    !!ERROR!! El algoritmo '$alg' no ordenó correctamente en el intento ${intento + 1}")
                }
                
                if (numIntentos > 1) {
                    println("    Intento ${intento + 1}: ${"%.3f".format(tiempo)} ms")
                }
            }
            
            // Calcular estadisticas
            if (tiempos.isNotEmpty()) {
                val minTiempo = calcularMinimo(tiempos)
                val maxTiempo = calcularMaximo(tiempos)
                val promedio = calcularPromedio(tiempos)
                val desviacion = if (tiempos.size > 1) {
                    calcularDesviacion(tiempos, promedio)
                } else {
                    0.0
                }
                
                // Mostrar resumen
                println("    Resumen $alg (n=$n):")
                println("      Mínimo: ${"%.3f".format(minTiempo)} ms")
                println("      Máximo: ${"%.3f".format(maxTiempo)} ms")
                println("      Promedio: ${"%.3f".format(promedio)} ms")
                if (numIntentos > 1) {
                    println("      Desviación: ${"%.3f".format(desviacion)} ms")
                }
                
                // Guardar datos para la grafica
                resultadosGrafica.add(
                    DatosGrafica(
                        algoritmo = alg,
                        tamano = n,
                        tiempoMin = minTiempo,
                        tiempoProm = promedio,
                        tiempoMax = maxTiempo
                    )
                )
                
                // Almacenar resultados por algoritmo
                resultadosPorAlgoritmo.add(
                    ResultadoAlgoritmo(alg, n, tiempos)
                )
            }
            println()
        }
    }

    // Generar grafica si se especifico nombre y hay multiples tamannos
    if (nombreFigura != null && tamanos.size > 1) {
        println("=== Generando Graficas ===")
        
        // Contar algoritmos unicos
        val algoritmosUnicos = Array(resultadosGrafica.size) { i -> resultadosGrafica[i].algoritmo }
            .distinctBy { it }
            .toTypedArray()
        
        // Para cada algoritmo, obtener los datos en el orden correcto
        for (alg in algoritmosUnicos) {
            val datosFiltrados = arrayListOf<DatosGrafica>()
            for (i in resultadosGrafica.indices) {
                if (resultadosGrafica[i].algoritmo == alg) {
                    datosFiltrados.add(resultadosGrafica[i])
                }
            }
            
            for (i in 0 until datosFiltrados.size - 1) {
                for (j in i + 1 until datosFiltrados.size) {
                    if (datosFiltrados[i].tamano > datosFiltrados[j].tamano) {
                        val temp = datosFiltrados[i]
                        datosFiltrados[i] = datosFiltrados[j]
                        datosFiltrados[j] = temp
                    }
                }
            }
            
            // Preparar arreglos para plotRuntime
            val tamanosArray = Array<Int>(datosFiltrados.size) { i -> datosFiltrados[i].tamano }
            val minTimesArray = Array<Double>(datosFiltrados.size) { i -> datosFiltrados[i].tiempoMin }
            val avgTimesArray = Array<Double>(datosFiltrados.size) { i -> datosFiltrados[i].tiempoProm }
            val maxTimesArray = Array<Double>(datosFiltrados.size) { i -> datosFiltrados[i].tiempoMax }
            val labelsArray = Array(datosFiltrados.size) { alg }
            
            try {
                plotRuntime(
                    windowTitle = "Comparacion de Algoritmos de Ordenamiento.",
                    imgPath = ".",
                    imgName = "${nombreFigura}_$alg.png",
                    title = "Algoritmo $alg - Tipo: $tipoArreglo",
                    xLabel = "Tamanno del arreglo",
                    yLabel = "Tiempo (ms)",
                    algorithmsLabels = labelsArray,
                    numElements = tamanosArray,
                    minTimes = minTimesArray,
                    averageTimes = avgTimesArray,
                    maxTimes = maxTimesArray
                )
                println("Grafica generada: ${nombreFigura}_$alg.png")
            } catch (e: Exception) {
                println("!!Error!! generando gráfica para $alg: ${e.message}")
            }
        }
        
        // Grafica combinada con todos los algoritmos
        if (algoritmosUnicos.size > 1) {
            try {
                val allLabels = Array(resultadosGrafica.size) { i -> resultadosGrafica[i].algoritmo }
                val allTamanos = Array<Int>(resultadosGrafica.size) { i -> resultadosGrafica[i].tamano }
                val allMinTimes = Array<Double>(resultadosGrafica.size) { i -> resultadosGrafica[i].tiempoMin }
                val allAvgTimes = Array<Double>(resultadosGrafica.size) { i -> resultadosGrafica[i].tiempoProm }
                val allMaxTimes = Array<Double>(resultadosGrafica.size) { i -> resultadosGrafica[i].tiempoMax }
                
                plotRuntime(
                    windowTitle = "Comparacion de Algoritmos de Ordenamiento",
                    imgPath = ".",
                    imgName = "${nombreFigura}_completo.png",
                    title = "Todos los Algoritmos - Tipo: $tipoArreglo",
                    xLabel = "Tamaño del arreglo",
                    yLabel = "Tiempo (ms)",
                    algorithmsLabels = allLabels,
                    numElements = allTamanos,
                    minTimes = allMinTimes,
                    averageTimes = allAvgTimes,
                    maxTimes = allMaxTimes
                )
                println("Grafica combinada generada: ${nombreFigura}_completo.png")
            } catch (e: Exception) {
                println("!!Error!! generando gráfica combinada: ${e.message}")
            }
        }
    }
    
    println("=== Prueba Finalizada ===")
}


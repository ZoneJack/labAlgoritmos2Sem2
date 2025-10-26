/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 *  Actividad 3: Implementacion de una solucion algoritmica
 *  que recibe como entrada secuencia de numeros enteros en forma de Arreglo A[i]
 *  * y retornar el numeros de elementos menores que A[i] tal que A < i.
 */

/**
 * Programa principal
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Error: Debes proporcionar numeros como argumentos.")
        return
    }
    // Verificacion que los datos de entrada sean correctos.

    try {
        val numeros = args.map { it.toInt() }
        val resultado = calcularMenoresALaDerecha(numeros)

        println(resultado.joinToString(" "))
    } catch (e: NumberFormatException) {
        println("Error> Todos los argumentos deben ser numeros enteros validos.")
    }
}

fun calcularMenoresALaDerecha(arr: List<Int>): List<Int> {
    val resultado = mutableListOf<Int>()
    val n = arr.size

    for (i in 0 until n) {
        var contador = 0

        for (j in i + 1 until n) {
            if (arr[j] < arr[i]){
                contador++
            }
        }
        resultado.add(contador)
    }
    return resultado
}
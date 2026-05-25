/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * EXAMEN 3 
 * Estudiante:
 * 	      Jose Daniel Torbet, Carnet: 21-10650.
 */
/**
 * Programa cliente Main.kt
 *
 */

fun main(args: Array<String>) {
    // Verificacion de argumentos
    if (args.isEmpty() || args.size % 2 != 0) {
        println("Error: Debe ingresar pares de numeros (Coeficiente Exponente).")
        println("Uso: ./runPolinomio.sh coef1 exp1 coef2 exp2 ...")
        println("Ejemplo para 3x^2 + 5: ./runPolinomio.sh 3 2 5 0")
        return
    }

    val p = Polinomio()

    println("////... Construyendo Polinomio desde Argumentos ... ////")
    
    var i = 0
    while (i < args.size) {

        try {
            val coef = args[i].toInt()
            val exp = args[i+1].toInt()
            
            p.agregarTermino(coef, exp)
            println("Agregado: ${coef}x^${exp}")
            
        } catch (e: NumberFormatException) {
            println("Error: '${args[i]}' o '${args[i+1]}' no es un numero valido.")
            return
        }
        i += 2
    }

    println("\n--- 1. Mostrar Polinomio ---")
    p.mostrarPolinomio()

    println("\n--- 2. Obtener Grado ---")
    println("Grado n: ${p.obtenerGrado()}")

    println("\n--- 3. Probar Iterador ---")

    print("Iterando (Descendente): ")
    for (termino in p) {
        print("[${termino.first}x^${termino.second}] ")
    }
    println()

    println("\n--- 4. Evaluando el Polinomio ---")
    println("Los valores 'x' de prueba son '0.0' '1.0' '2.0' '-1.0', esto puede ser modificado desde el Maik,kt")

    val valoresPrueba = doubleArrayOf(0.0, 1.0, 2.0, -1.0)
    for (z in valoresPrueba) {
        val res = p.evaluarPolinomio(z)
        println("P($z) = $res")
    }
}
 // Fin del Ejercicio

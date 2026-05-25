/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * EXAMEN 3 
 * Estudiante:
 * 	      Jose Daniel Torbet, Carnet: 21-10650.
 */
/**
 * TAD Polinomio, implementacion basada en lista enlazada simple.
 */
 class Polinomio : Iterable<Pair<Int, Int>> {
 	private class Celda(
        var coef: Int,
        var exp: Int, 
        var sig: Celda? 
    )
 	
 	private var poly: Celda? = null
 	private var n: Int = -1
 	// Constructor
 	init {
        this.poly = null
        this.n = -1
    }
    fun agregarTermino(ak: Int, k: Int) {
 	// Precondicion: ak distinto de 0 
        if (ak == 0 || k < 0) return

 		// primer caso, lista vacia o el nuevo termino tiene mayor grado que la cabeza
        if (poly == null || k > poly!!.exp) {
            val nueva = Celda(ak, k, poly)
            poly = nueva
            actualizarGrado()
            return
        }

        var actual = poly
        var anterior: Celda? = null

        while (actual != null && actual.exp > k) {
            anterior = actual
            actual = actual.sig
        }

 		// Segundo caso, el exponente ya existe.
        if (actual != null && actual.exp == k) {
            actual.coef += ak
            // Si el coeficiente se vuelve 0, hay que eliminar el nodo
            if (actual.coef == 0) {
                if (anterior == null) { // Era la cabeza
                    poly = actual.sig
                } else {
                    anterior.sig = actual.sig
                }
            }
        } else {
 		// Tercer caso, insertar el nuevo nodo
            val nueva = Celda(ak, k, actual)
            if (anterior == null) {
                poly = nueva // Esto no es probable, pero nunca esta de mas asegurar
            } else {
                anterior.sig = nueva
            }
        }
        
        actualizarGrado()
    }
 
 
 // Funciones auxiliares para actualizar los grados, calcular potencias (Para evaluar el polinomio), imprimir de forma ascendente y descendente
 

 private fun actualizarGrado() {
        this.n = if (poly != null) poly!!.exp else -1
    }

    fun obtenerGrado(): Int {
        return this.n
    }

    fun evaluarPolinomio(z: Double): Double {
        var resultado: Double = 0.0
        var actual = poly

        while (actual != null) {

            resultado += actual.coef * potencia(z, actual.exp)
            actual = actual.sig
        }
        return resultado
    }


    private fun potencia(base: Double, exp: Int): Double {
        if (exp == 0) return 1.0
        var res = 1.0
        var i = 0
        while (i < exp) {
            res *= base
            i++
        }
        return res
    }

    fun mostrarPolinomio() {
        print("P(x) = ")
        if (poly == null) {
            println("0 (Grado: $n)")
        } else {
            imprimirRecursivo(poly)
            println(" (Grado: $n)")
        }
    }

    private fun imprimirRecursivo(nodo: Celda?) {
        if (nodo == null) return
        
        imprimirRecursivo(nodo.sig)

        if (nodo.coef > 0) print("+")
        print("${nodo.coef}x^${nodo.exp} ")
    }


    override fun iterator(): Iterator<Pair<Int, Int>> {
        return object : Iterator<Pair<Int, Int>> {
            var actual = poly

            override fun hasNext(): Boolean {
                return actual != null
            }

            override fun next(): Pair<Int, Int> {
                if (actual == null) throw Exception("No hay más elementos") 
                val par = Pair(actual!!.coef, actual!!.exp)
                actual = actual!!.sig
                return par
            }
        }
    }
}
 // Fin del Ejercicio

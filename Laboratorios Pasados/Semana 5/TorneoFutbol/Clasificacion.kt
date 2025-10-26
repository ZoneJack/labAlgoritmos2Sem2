import java.io.File
import java.io.BufferedReader

data class Equipo(
    val nombre: String,
    var puntos: Int = 0,
    var juegos: Int = 0,
    var ganados: Int = 0,
    var empatados: Int = 0,
    var perdidos: Int = 0,
    var golesFavor: Int = 0,
    var golesContra: Int = 0
) {
    fun diferenciaGoles(): Int = golesFavor - golesContra
}

fun compararEquipos(a: Equipo, b: Equipo): Int {
    return when {
        a.puntos != b.puntos -> b.puntos - a.puntos
        a.ganados != b.ganados -> b.ganados - a.ganados
        a.diferenciaGoles() != b.diferenciaGoles() -> b.diferenciaGoles() - a.diferenciaGoles()
        a.golesFavor != b.golesFavor -> b.golesFavor - a.golesFavor
        a.juegos != b.juegos -> a.juegos - b.juegos
        else -> a.nombre.lowercase().compareTo(b.nombre.lowercase())
    }
}

/**
 * Funcion auxiliar para intercambiar dos elementos en un arreglo.
 */
fun swap(A: Array<Equipo>, i: Int, j: Int) {
    val temp = A[i]
    A[i] = A[j]
    A[j] = temp
}

fun quicksort(A: Array<Equipo>, p: Int = 0, r: Int = A.size - 1) {
    if (p < r) {
        val q = partition(A, p, r)
        quicksort(A, p, q - 1)
        quicksort(A, q + 1, r)
    }
}

fun partition(A: Array<Equipo>, p: Int, r: Int): Int {
    val x = A[r] 
    var i = p - 1
    for (j in p until r) {
        if (compararEquipos(A[j], x) <= 0) {
            i++
            swap(A, i, j)
        }
    }
    swap(A, i + 1, r)
    return i + 1
}

fun encontrarEquipo(equipos: Array<Equipo>, nombre: String): Equipo? {
    for (equipo in equipos) {
        if (equipo.nombre == nombre) {
            return equipo
        }
    }
    return null
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Uso: ./runClasificacion <archivo_entrada>")
        return
    }

    val reader = File(args[0]).bufferedReader()
    val numTorneos = reader.readLine().toInt()

    repeat(numTorneos) {
        val nombreTorneo = reader.readLine()
        val numEquipos = reader.readLine().toInt()
        val equipos = Array(numEquipos) {
            Equipo(reader.readLine())
        }

        val numJuegos = reader.readLine().toInt()
        repeat(numJuegos) {
            val partes = reader.readLine().split(" ")
            val eq1Nombre = partes[0]
            val goles1 = partes[1].toInt()
            val eq2Nombre = partes[2]
            val goles2 = partes[3].toInt()
            
            val e1 = encontrarEquipo(equipos, eq1Nombre)!!
            val e2 = encontrarEquipo(equipos, eq2Nombre)!!
            e1.golesFavor += goles1
            e1.golesContra += goles2
            e2.golesFavor += goles2
            e2.golesContra += goles1
            e1.juegos++
            e2.juegos++

            when {
                goles1 > goles2 -> {
                    e1.puntos += 3
                    e1.ganados++
                    e2.perdidos++
                }
                goles1 < goles2 -> {
                    e2.puntos += 3
                    e2.ganados++
                    e1.perdidos++
                }
                else -> {
                    e1.puntos++
                    e2.puntos++
                    e1.empatados++
                    e2.empatados++
                }
            }
        }

        quicksort(equipos)

        println(nombreTorneo)
        for((i, e) in equipos.withIndex()) {
            println("${i + 1}) ${e.nombre} ${e.puntos} pts, ${e.juegos} juegos, " +
                    "(${e.ganados} - ${e.empatados} - ${e.perdidos}), " +
                    "${e.diferenciaGoles()} difGoles, (${e.golesFavor} - ${e.golesContra}")
        }

        if (it < numTorneos - 1) {
            println()
        }
    }
}
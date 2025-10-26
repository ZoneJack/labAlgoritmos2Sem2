/**
 * Universidad Simon Bolivar
 * Departamento de Computacion y Tecnologia de la Informacion
 * CI2692 - Laboratorio de Algoritmos y Estructuras 2
 * Estudiantes:
 *            Arianna Martinez, carnet: 21-10358
 *            Jose Torbet, carnet: 21-10650
 *  Actividad 2: Implementacion del Algoritmo de Strassen.
 */


fun multiplicacionStrassen(matriz1: Array<Array<Int>>, matriz2: Array<Array<Int>>): Array<Array<Int>> {
  val n = matriz1.size

  // Validamos que las matrices no esten vacias y sean cuadradas
  if (n == 0 || n != matriz1[0].size || n != matriz2.size || n != matriz2[0].size) {
    throw IllegalArgumentException("Las matrices deben ser cuadradas y no vacias.")
  }

  // Encontramos la siguiente potencia de 2 para el padding
  var m = 1
  while (m < n) {
    m *= 2
  }

  // Si la dimension ya es potencia de 2, no se necesita padding
  if (m == n) {
    return strassenRecursive(matriz1, matriz2)
  }

  // Creamos matrices de tamaño m x m con padding
  val aPadded = Array(m) { Array(m) { 0 } }
  val bPadded = Array(m) { Array(m) { 0 } }

  for (i in 0 until n) {
    for (j in 0 until n) {
        aPadded[i][j] = matriz1[i][j]
        bPadded[i][j] = matriz2[i][j]
    }
  } 

  // Llamamos a la funcion recursiva con las matrices 
  val cPadded = strassenRecursive(aPadded, bPadded)

  //Extraemos del resultado la submatriz de tamaño n x n
  val result = Array(n) { Array(n) { 0 } }
  for (i in 0 until n) {
    for (j in 0 until n) {
      result[i][j] = cPadded[i][j]
    }
  }

  return result
}

/**
 * Implementación recursiva del algoritmo de Strassen.
 * Esta función asume que las matrices de entrada son cuadradas
 * y su dimensión es una potencia de dos.
 */
private fun strassenRecursive(A: Array<Array<Int>>, B: Array<Array<Int>>): Array<Array<Int>> {
  val n = A.size
  val C = Array(n) { Array(n) { 0 } }

  // Caso base: si la matriz es 1x1
  if (n ==1) {
    C[0][0] = A[0][0] * B[0][0]
    return C
  }

  // Dividimos las matrices A y B en 4 submatrices cada una
  val newSize = n / 2
  val A11 = Array(newSize) { Array(newSize) { 0 } }
  val A12 = Array(newSize) { Array(newSize) { 0 } }
  val A21 = Array(newSize) { Array(newSize) { 0 } }
  val A22 = Array(newSize) { Array(newSize) { 0 } }
  val B11 = Array(newSize) { Array(newSize) { 0 } }
  val B12 = Array(newSize) { Array(newSize) { 0 } }
  val B21 = Array(newSize) { Array(newSize) { 0 } }
  val B22 = Array(newSize) { Array(newSize) { 0 } }

  for (i in 0 until newSize) {
    for (j in 0 until newSize) {
      A11[i][j] = A[i][j]
      A12[i][j] = A[i][j + newSize]
      A21[i][j] = A[i + newSize][j]
      A22[i][j] = A[i + newSize][j + newSize]

      B11[i][j] = B[i][j]
      B12[i][j] = B[i][j + newSize]
      B21[i][j] = B[i + newSize][j]
      B22[i][j] = B[i + newSize][j + newSize]
    }
  }

  // Calculamos las 7 multiplicaciones de Strassen (P1 a P7) recursivamente
  val P1 = strassenRecursive(A11, substract(B12, B22))
  val P2 = strassenRecursive(add(A11, A12), B22)
  val P3 = strassenRecursive(add(A21, A22), B11)
  val P4 = strassenRecursive(A22, substract(B21, B11))
  val P5 = strassenRecursive(add(A11, A22), add(B11, B22))
  val P6 = strassenRecursive(substract(A12, A22), add(B21, B22))
  val P7 = strassenRecursive(substract(A11, A21), add(B11, B12))

  // Combinamos las 7 multiplicaciones para obtener las submatrices del resultado C
  val C11 = add(substract(add(P5, P4), P2), P6)
  val C12 = add(P1, P2)
  val C21 = add(P3, P4)
  val C22 = substract(substract(add(P5, P1), P3), P7)

  // Unimos las 4 submatrices en la matriz resultado C
  for (i in 0 until newSize) {
    for (j in 0 until newSize) {
      C[i][j] = C11[i][j]
      C[i][j + newSize] = C12[i][j]
      C[i + newSize][j] = C21[i][j]
      C[i + newSize][j + newSize] = C22[i][j]
    }
  }

  return C
}

// Función para sumar dos matrices
private fun add(A: Array<Array<Int>>, B: Array<Array<Int>>): Array<Array<Int>> {
  val n = A.size
  val C = Array(n) { Array(n) { 0 } }
  for (i in 0 until n) {
    for (j in 0 until n) {
      C[i][j] = A[i][j] + B[i][j]
    }
  }
  return C
} 

// Función para restar dos matrices
private fun substract(A: Array<Array<Int>>, B: Array<Array<Int>>): Array<Array<Int>> {
  val n = A.size
  val C = Array(n) { Array(n) { 0 } }
  for (i in 0 until n) {
    for (j in 0 until n) {
      C[i][j] = A[i][j] - B[i][j]
    }
  }
  return C
}
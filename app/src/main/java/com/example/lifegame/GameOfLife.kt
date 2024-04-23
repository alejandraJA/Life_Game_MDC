package com.example.lifegame

/**
 * Clase que representa el Juego de la Vida de Conway.
 *
 * El Juego de la Vida es un autómata celular diseñado por el matemático británico John Horton
 * Conway en 1970.
 *
 * El juego se desarrolla en un tablero bidimensional de celdas cuadradas, cada una de las cuales
 * puede estar viva o muerta.
 *
 * Las reglas del juego son las siguientes:
 * - Cualquier célula viva con menos de dos vecinos vivos muere.
 * - Cualquier célula viva con dos o tres vecinos vivos vive hasta la siguiente generación.
 * - Cualquier célula viva con más de tres vecinos vivos muere.
 * - Cualquier célula muerta con exactamente tres vecinos vivos se convierte en una célula viva.
 * - Las células muertas con menos de tres vecinos vivos siguen muertas.
 * - Las células muertas con más de tres vecinos vivos siguen muertas.
 *
 * @param boardSize Tamaño del tablero.
 */
class GameOfLife(var boardSize: Int) {
    private val board = Array(boardSize) { BooleanArray(boardSize) }

    fun initializeRandom() {
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = Math.random() < 0.5
            }
        }
    }

    /**
     * Evoluciona el tablero al siguiente estado.
     * - La función evolve no toma ningún parámetro y su objetivo es actualizar el estado del
     * tablero para la siguiente generación según las reglas del Juego de la Vida.
     * - Primero, se crea un nuevo tablero newBoard con las mismas dimensiones que el tablero
     * actual board. Este nuevo tablero se utilizará para almacenar el estado del tablero en
     * la siguiente generación.
     * - Luego, se ejecutan dos bucles for anidados que recorren cada celda del tablero actual.
     * - Para cada celda, se calcula el número de vecinos vivos utilizando la función
     * countNeighbors.
     * - Luego, se actualiza el estado de la celda correspondiente en el nuevo tablero según
     * las reglas del Juego de la Vida:
     * * Si la celda actual está viva (es decir, board[i][j] es verdadero), entonces permanecerá
     * viva en la siguiente generación solo si tiene exactamente 2 o 3 vecinos vivos.
     * * Si la celda actual está muerta (es decir, board[i][j] es falso), entonces se convertirá
     * en una celda viva en la siguiente generación solo si tiene exactamente 3 vecinos vivos.
     * - Finalmente, después de calcular el estado de todas las celdas para la siguiente generación,
     * se actualiza el tablero actual con los valores del nuevo tablero. Esto se hace con otros dos
     * bucles for anidados que recorren cada celda del tablero.
     */
    fun evolve() {
        val newBoard = Array(board.size) { BooleanArray(board[0].size) }
        // Recorrer el tablero
        for (i in board.indices) {
            // Recorrer las celdas de la fila
            for (j in board[i].indices) {
                val neighbors = countNeighbors(i, j)
                newBoard[i][j] =
                    if (board[i][j]) {
                        // Regla 1: Cualquier célula viva con menos de dos vecinos vivos muere.
                        // Regla 2: Cualquier célula viva con dos o tres vecinos vivos vive hasta la siguiente generación.
                        // Regla 3: Cualquier célula viva con más de tres vecinos vivos muere.
                        neighbors == 2 || neighbors == 3
                    } else {
                        // Regla 4: Cualquier célula muerta con exactamente tres vecinos vivos se convierte en una célula viva.
                        // Regla 5: Cualquier célula muerta con menos de tres vecinos vivos sigue muerta.
                        // Regla 6: Cualquier célula muerta con más de tres vecinos vivos sigue muerta.
                        neighbors == 3
                    }
            }
        }
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = newBoard[i][j]
            }
        }
    }

    /**
     * Cuenta el número de vecinos vivos de una celda.
     *
     * La celda tiene 8 vecinos, que son las celdas adyacentes en horizontal, vertical y diagonal.
     *
     * - La función countNeighbors toma dos parámetros, x e y, que representan las coordenadas de la
     * celda en el tablero.
     * - Se inicializa una variable count en 0. Esta variable se utilizará para contar el número
     * de vecinos vivos.
     * - Luego, se ejecutan dos bucles for anidados que iteran desde -1 hasta 1. Estos bucles se
     * utilizan para verificar todas las celdas alrededor de la celda actual (la celda en las
     * coordenadas x e y).
     * - Dentro de los bucles, se calculan las coordenadas neighborX y neighborY de la celda vecina
     * sumando i y j a x e y respectivamente.
     * - Luego, hay una condición if que verifica si las coordenadas de la celda vecina son válidas
     * (es decir, están dentro de los límites del tablero) y si la celda vecina no es la celda
     * actual (es decir, i y j no son ambos 0).
     * - Si la celda vecina es válida y no es la celda actual, entonces se verifica si la celda
     * vecina está viva (es decir, board[neighborX][neighborY] es verdadero). Si la celda vecina
     * está viva, se incrementa el contador count.
     * - Finalmente, después de verificar todas las celdas vecinas, la función devuelve el
     * contador count, que representa el número de vecinos vivos de la celda actual.
     *
     * @param x Coordenada x de la celda.
     * @param y Coordenada y de la celda.
     * @return Número de vecinos vivos.
     * @see [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
     * @see [Reglas del juego](https://es.wikipedia.org/wiki/Juego_de_la_vida)
     *
     */
    private fun countNeighbors(x: Int, y: Int): Int {
        var count = 0
        // Recorrer los vecinos de la celda
        for (i in -1..1) {
            // Recorrer las celdas de la fila
            for (j in -1..1) {
                val neighborX = x + i
                val neighborY = y + j
                // Verificar si la celda vecina es válida y no es la celda actual
                if (neighborX in board.indices && neighborY in 0 until board[0].size && !(i == 0 && j == 0)) {
                    // Verificar si la celda vecina está viva
                    if (board[neighborX][neighborY]) {
                        // Incrementar el contador de vecinos vivos
                        count++
                    }
                }
            }
        }
        return count
    }

    /**
     * Comprueba si una celda está viva.
     * - La función isCellAlive toma dos parámetros, x e y, que representan las coordenadas de la
     * celda en el tablero.
     * - Si el valor de la celda es verdadero, significa que la celda está viva. Si el valor de
     * la celda es falso, significa que la celda está muerta.
     *
     * @param x Coordenada x de la celda.
     * @param y Coordenada y de la celda.
     * @return Verdadero si la celda está viva, falso si la celda está muerta.
     */
    fun isCellAlive(x: Int, y: Int): Boolean {
        return board[x][y]
    }

    /**
     * Establece si una celda está viva o muerta.
     * - La función setLiveOrDie toma un parámetro position que representa la posición de la celda
     * en el tablero en un formato unidimensional.
     * - La posición se convierte en las coordenadas x e y de la celda dividiendo la posición por
     * el tamaño del tablero.
     * - Luego, se invierte el valor de la celda en el tablero (es decir, si la celda está viva,
     * se convierte en muerta, y si la celda está muerta, se convierte en viva).
     *
     * @param position Posición de la celda en el tablero en un formato unidimensional.
     */
    fun setLiveOrDie(position: Int) {
        val x = position / boardSize
        val y = position % boardSize
        board[x][y] = !board[x][y]
    }
}

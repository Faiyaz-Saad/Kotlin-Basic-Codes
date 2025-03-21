package com.faiyazAnnoor
import java.util.Scanner
import java.text.DecimalFormat
class Display{
    val SIZE : Int = 3
    fun display(matrix : Array < DoubleArray > ){
        val decimalFormat : DecimalFormat = DecimalFormat("#.##")
        for(i : Int in matrix.indices){
            for(j : Int in matrix[i].indices){
                print("${decimalFormat.format(matrix[i][j].toDouble())}  ")
            }
            println()
        }
    }
}
class Determinent {
    fun determinent(matrix: Array<DoubleArray>, n: Int): Double {
        val display: Display = Display()
        if (n == 1) {
            return matrix[0][0]
        } else if (n == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0])
        } else {
            val temp: Array<DoubleArray> = Array(display.SIZE) { DoubleArray(display.SIZE) }
            var det: Double = 0.0
            var sign: Int = -1
            for (f: Int in 0 until display.SIZE) {
                var subi: Int = 0
                for (i: Int in 1 until display.SIZE) {
                    var subj: Int = 0
                    for (j: Int in 0 until display.SIZE) {
                        if (j == f) {
                            continue
                        } else {
                            temp[subi][subj] = matrix[i][j]
                            subj++
                        }
                    }
                    subi++
                }
                det = det + (-sign) * matrix[0][f] * determinent(temp, n - 1)
            }
            return det
        }
    }
}
class CofectorMatrix{
    fun cofectorMatrix(matrix: Array<DoubleArray>, p : Int, q : Int, size: Int) : Double{
        val temp : Array<DoubleArray> = Array(size){DoubleArray(size)}
        val determinent : Determinent = Determinent()
        var sign :Int = -1
        var subi : Int= 0
        for(row : Int in matrix.indices){
            var subj : Int = 0
            for(column : Int in matrix[row].indices){
                if((row != p) && (column !=q)){
                    temp[subi][subj] = matrix[row][column]
                    subj++
                    if(subj == 2){
                        subi++
                    }
                }
            }
        }
        return ((-sign) * determinent.determinent(temp ,size - 1))
    }
}
class AdjointMatrix{
    fun adjointMatrix(matrix: Array<DoubleArray> , adj : Array<DoubleArray> ,size : Int){
        val cofectorMatrix : CofectorMatrix = CofectorMatrix()
        for(k : Int in 0 until size){
            for(l : Int in 0 until size){
                adj[l][k] = cofectorMatrix.cofectorMatrix(matrix , k , l , size)
            }
        }
    }
}
class InverseMatrix{
    fun inverseMatrix(matrix: Array<DoubleArray>, inv : Array<DoubleArray>){
        val determinent : Determinent = Determinent()
        val display : Display = Display()
        val adjointMatrix : AdjointMatrix = AdjointMatrix()
        val det : Double = determinent.determinent(matrix,display.SIZE)
        val adj : Array<DoubleArray> = Array(display.SIZE){DoubleArray(display.SIZE)}
        adjointMatrix.adjointMatrix(matrix,adj ,display.SIZE)
        for(s : Int in adj.indices){
            for(t : Int in adj[s].indices){
                inv[s][t] = adj[s][t] / det
            }
        }
    }
}
object Testing{
    @JvmStatic
    fun main( args : Array <String>){
        val display :  Display = Display()
        val inverseMatrix : InverseMatrix = InverseMatrix()
        val input : Scanner = Scanner(System.`in`)
        val matrix : Array <DoubleArray> = Array(display.SIZE){DoubleArray(display.SIZE)}
        val inv : Array < DoubleArray > = Array(display.SIZE){DoubleArray(display.SIZE)}
        for (row : Int in matrix.indices){
            for(column : Int in matrix[row].indices){
                print("matrix[$row][$column]: ")
                matrix[row][column] = input.nextDouble()
            }
        }
        inverseMatrix.inverseMatrix(matrix,inv)
        println("Matrix: ")
        display.display(matrix)
        println("Inverse Matrix: ")
        display.display(inv)
        input.close()
    }
}
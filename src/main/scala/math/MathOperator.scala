package pl.piasta.lincalc.scala
package math

enum MathOperator(val sign: Char) {
    case Add extends MathOperator('+')
    case Subtract extends MathOperator('-')
    case Multiply extends MathOperator('*')
    case Divide extends MathOperator('/')
}

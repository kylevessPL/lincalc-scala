package pl.piasta.lincalc.scala
package math

import ch.obermuhlner.math.big.DefaultBigDecimalMath

import scala.util.Using

object MathExtensions:
    private val PRECISION = 32

    extension (value: BigDecimal)
        def percentage: BigDecimal = value / BigDecimal(100)

        def sqrt: BigDecimal = Using(createLocalMathContext(PRECISION)) {
            DefaultBigDecimalMath.sqrt(value)
        }

        def sin: BigDecimal = Using(createLocalMathContext(PRECISION)) {
            DefaultBigDecimalMath.sin(value)
        }

        def cos: BigDecimal = Using(createLocalMathContext(PRECISION)) {
            DefaultBigDecimalMath.cos(value)
        }

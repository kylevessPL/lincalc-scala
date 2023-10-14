package pl.piasta.lincalc.scala
package math

import common.Constants.PRECISION

import ch.obermuhlner.math.big.DefaultBigDecimalMath
import ch.obermuhlner.math.big.DefaultBigDecimalMath.createLocalMathContext

import scala.util.Using
import scala.util.Using.resource

object MathExtensions:
    extension (value: BigDecimal)
        def percentage: BigDecimal = value / BigDecimal(100)

        def sqrt: BigDecimal = resource(createLocalMathContext(PRECISION)) { _ =>
            DefaultBigDecimalMath.sqrt(value.bigDecimal)
        }

        def sin: BigDecimal = resource(createLocalMathContext(PRECISION)) { _ =>
            DefaultBigDecimalMath.sin(value.bigDecimal)
        }

        def cos: BigDecimal = resource(createLocalMathContext(PRECISION)) { _ =>
            DefaultBigDecimalMath.cos(value.bigDecimal)
        }

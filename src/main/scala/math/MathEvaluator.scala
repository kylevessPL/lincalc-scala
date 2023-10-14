package pl.piasta.lincalc.scala
package math

import common.Constants.PRECISION

import java.math.BigDecimal
import javax.script.ScriptEngineManager
import scala.util.Try

object MathEvaluator {
    private lazy val scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript")

    def evaluateExpression(expression: String): Option[BigDecimal] = Try {
        val evalScript = createEvalScript(expression)
        val result = scriptEngine.eval(evalScript).toString
        Some(BigDecimal(result))
    }.getOrElse(None)

    private def createEvalScript(expression: String): String = s"parseFloat(($expression).toFixed($PRECISION))"
}

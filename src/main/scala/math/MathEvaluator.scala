package pl.piasta.lincalc.scala
package math

import java.math.BigDecimal
import javax.script.{ScriptEngineManager, ScriptException}

object MathEvaluator {
    private lazy val scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript")

    def evaluateExpression(expression: String): Option[BigDecimal] = {
        try {
            val result = scriptEngine.eval(expression).toString
            Some(BigDecimal(result))
        } catch {
            case _: ScriptException => None
        }
    }
}

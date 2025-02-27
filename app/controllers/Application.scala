package controllers

import play.api._
import play.api.mvc._
import play.api.cache.Cache
import play.api.Play.current
import javax.measure.unit.SI.KILOGRAM
import javax.measure.quantity.Mass
import org.jscience.physics.model.RelativisticModel
import org.jscience.physics.amount.Amount

import play.api.db._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index(null))
  }
object Application extends Controller {

  def index = Action {
    RelativisticModel.select()
    val energy = scala.util.Properties.envOrElse("ENERGY", "12 GeV")
    val m = Amount.valueOf("12 GeV").to(KILOGRAM)
    val testRelativity = s"E=mc^2: 12 GeV = $m"
    Ok(views.html.index(testRelativity))
  }
  def db = Action {
    var out = ""
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())")

      val rs = stmt.executeQuery("SELECT tick FROM ticks")

      while (rs.next) {
        out += "Read from DB: " + rs.getTimestamp("tick") + "\n"
      }
    } finally {
      conn.close()
    }
    Ok(out)
  }
}

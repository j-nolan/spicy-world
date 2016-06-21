package controllers

import models._
import javax.inject._
import play.api.mvc._

import slick.driver.MySQLDriver.api._
import scala.concurrent._
import scala.concurrent.duration._
import slick.jdbc.meta._
  

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ChiliController @Inject() extends Controller {
  private val db = Database.forConfig("h2mem1")
   
  
  /**
   * Print creation form
   */
  def chiliForm() = Action {
    Ok(views.html.chiliform(Countries.all().map((country) => (country.id.get -> country.name)).toMap))
  }

  /**
   *
   */
  def chiliList() = Action {
    Ok(views.html.chililist(Chilies.allWithSauceCount))
  }
  
  
  /**
   * insert to DB
   */
  def createChili() = Action(parse.tolerantFormUrlEncoded) {
    request => {
      val chili_name = request.body.get("chili_name").map(_.head)
      val chili_country = request.body.get("chili_country").map(_.head)
      
      if (chili_name.isEmpty || chili_name.isEmpty) {
        Ok(views.html.message("Oops! Remplissez tous les champs")("error"))
      }
      
      Chilies.insert(chili_name.get, chili_country.get.toInt)
      
      Ok(views.html.message("Sauce " + chili_name.get + "ajoutée correctement ! Merci")("confirm"))
    }
  }
}

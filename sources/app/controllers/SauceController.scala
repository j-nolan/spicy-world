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
class SauceController @Inject() extends Controller {
  private val db = Database.forConfig("h2mem1")
   
  /**
   * List all sauces
   */
  def sauceList() = Action {
    Ok(views.html.saucelist(Sauces.all))
  }

  /**
   * SHow one single sauce
   */
  def singleSauce(id: Int) = Action {
    Ok(views.html.singlesauce(Sauces.byId(id)))
  }
  /**
   * Print creation form
   */
  def sauceForm() = Action {
    Ok(views.html.sauceform(
      Chilies.all().map(chili => (chili.id.get -> chili.name)).toMap,
      Flavours.all().map(flavour => (flavour.id.get -> flavour.name)).toMap
    ))
  }
  
  /**
   * Create a sauce
   */
  def createSauce() = Action(parse.tolerantFormUrlEncoded) {
    request => {
      val sauce_name = request.body.get("sauce_name").map(_.head)
      val sauce_force = request.body.get("sauce_force").map(_.head)
      val sauce_chilies = request.body.get("sauce_chilies")
      val sauce_flavours = request.body.get("sauce_flavours")
      val sauce_image = request.body.get("sauce_image").map(_.head)
      
      if (sauce_name.isEmpty || sauce_force.isEmpty || sauce_chilies.isEmpty || sauce_flavours.isEmpty || sauce_image.isEmpty) {
        Ok(views.html.message("Oops! Remplissez tous les champs")("error"))
      }
      
      
      Sauces.insert(sauce_name.get,
                    sauce_force.get,
                    sauce_chilies.get.toList.map(_.toInt),
                    sauce_flavours.get.toList.map(_.toInt),
                    sauce_image.get)
      
      Ok(views.html.message("Sauce " + sauce_name.get + " ajoutée correctement ! Merci")("confirm"))
    }
  }
}

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
class FlavourController @Inject() extends Controller {
  private val db = Database.forConfig("h2mem1")
   
  
  /**
   * Print creation form
   */
  def flavourForm() = Action {
    Ok(views.html.flavourform())
  }

  /**
   * List of flavours
   */
  def flavourList() = Action {
    Ok(views.html.flavourlist(Flavours.allWithSauceCount))
  }
  
  
  /**
   * insert to DB
   */
  def createFlavour() = Action(parse.tolerantFormUrlEncoded) {
    request => {
      val flavour_name = request.body.get("flavour_name").map(_.head)
      
      if (flavour_name.isEmpty) {
        Ok(views.html.message("Oops! Remplissez tous les champs")("error"))
      }
      
      Flavours.insert(flavour_name.get)
      
      Ok(views.html.message("Saveur "  + flavour_name + "ajoutée correctement ! Merci")("confirm"))
    }
  }
}

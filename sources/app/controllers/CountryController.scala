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
class CountryController @Inject() extends Controller {
  private val db = Database.forConfig("h2mem1")
   
  
  /**
   * Print creation form
   */
  def countryForm() = Action {
    Ok(views.html.countryform())
  }

  /**
   * Country list
   */
  def countryList() = Action {
    Ok(views.html.countrylist(Countries.allWithChiliCount))
  }
  
  
  /**
   * insert to DB
   */
  def createCountry() = Action(parse.tolerantFormUrlEncoded) {
    request => {
      val country_name = request.body.get("country_name").map(_.head)
      
      if (country_name.isEmpty) {
        Ok(views.html.message("Oops! Remplissez tous les champs")("error"))
      }
      
      
      Countries.insert(country_name.get)
      
      Ok(views.html.message("Pays " + country_name.get + " ajouté correctement ! Merci")("confirm"))
    }
  }
}

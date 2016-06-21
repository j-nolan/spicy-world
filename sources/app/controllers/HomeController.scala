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
class HomeController @Inject() extends Controller {
  private val db = Database.forConfig("h2mem1")
  
  
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index())
  }
}

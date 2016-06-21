package models

import slick.driver.MySQLDriver.api._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._


case class Sauce(name: String, force: String, image: String, id: Option[Int] = None)
case class SauceToChili(idSauce: Int, idChili: Int)
case class SauceToFlavour(idSauce: Int, idFlavour: Int)

class Sauces(tag: Tag) extends Table[Sauce](tag, "Sauce") {
  def id = column[Int]("idSauce", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def force = column[String]("strength")
  def image = column[String]("image")
  
  def * = (name, force, image, id.?) <> ((Sauce.apply _).tupled, Sauce.unapply)
}

// Many to many Relation table between sauces and chilies
class SaucesToChilies(tag: Tag) extends Table[SauceToChili](tag, "Sauce_Piment") {
  def sauceId = column[Int]("idSauce")
  def chiliId = column[Int]("idPiment")
  def * = (sauceId, chiliId) <> ((SauceToChili.apply _).tupled, SauceToChili.unapply)
}

// Many to many Relation table between sauces and flavours
class SaucesToFlavours(tag: Tag) extends Table[SauceToFlavour](tag, "Sauce_Saveur") {
  def data = TableQuery[SaucesToFlavours]
  def sauceId = column[Int]("idSauce")
  def flavourId = column[Int]("idSaveur")
  def * = (sauceId, flavourId) <> ((SauceToFlavour.apply _).tupled, SauceToFlavour.unapply)
}

object Sauces {
  private val db = Database.forConfig("h2mem1")
  lazy val data = TableQuery[Sauces]
  lazy val chiliRelation = TableQuery[SaucesToChilies]
  lazy val flavourRelation = TableQuery[SaucesToFlavours]
  
  def insert(name: String, force: String, chilies: List[Int], flavours: List[Int], image: String) = {
    val insertSauceQuery = data returning data.map(_.id) into ((sauce, id) => sauce.copy(id = Some(id)))
    val newSauce = Await.result(db.run(insertSauceQuery += Sauce(name, force, image)), Duration.Inf)

    // Insert chilies linked to that sauce
    chilies.foreach(chili => Await.result(db.run(DBIO.seq(chiliRelation += SauceToChili(newSauce.id.get, chili))), Duration.Inf))

    // Insert flavours linked to that sauce too
    flavours.foreach(flavour => Await.result(db.run(DBIO.seq(flavourRelation += SauceToFlavour(newSauce.id.get, flavour))), Duration.Inf))
  }
  
  def all(): List[(Sauce, List[Flavour])] = {
    val query = data joinLeft flavourRelation on (_.id === _.flavourId) joinLeft Flavours.data on (_._1.id === _.id)

    (for (s <- Await.result(db.run(query.result), Duration.Inf)) yield s)
    .map{t => (t._1._1, t._2.get)}
    .groupBy(_._1)
    .map { case (k,v) => (k,v.map(_._2).toList)}
    .toList
  }

  def byId(id: Int): (Sauce, List[Flavour]) = {
    val query = data filter(_.id === id) joinLeft flavourRelation on (_.id === _.flavourId) joinLeft Flavours.data on (_._1.id === _.id)
    val sauces = for (s <- Await.result(db.run(query.result), Duration.Inf)) yield s

    (for (s <- Await.result(db.run(query.result), Duration.Inf)) yield s)
    .map{t => (t._1._1, t._2.get)}
    .groupBy(_._1)
    .map { case (k,v) => (k,v.map(_._2).toList)}
    .toList
    .head
  }
  
}
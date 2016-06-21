package models

import slick.driver.MySQLDriver.api._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._


case class Flavour(name: String, id: Option[Int] = None)

class Flavours(tag: Tag) extends Table[Flavour](tag, "Saveur") {
  def id = column[Int]("idSaveur", O.PrimaryKey, O.AutoInc)
  def name = column[String]("nom")
  
  def * = (name, id.?) <> ((Flavour.apply _).tupled, Flavour.unapply)
}



object Flavours {
  private val db = Database.forConfig("h2mem1")
  lazy val data = TableQuery[Flavours]
  lazy val sauceRelation = TableQuery[SaucesToChilies]
  
  def insert(name: String) = Await.result(db.run(DBIO.seq(data += Flavour(name))), Duration.Inf)
  
  def all(): List[Flavour] = (for (s <- Await.result(db.run(data.result), Duration.Inf)) yield s).toList

  def allWithSauceCount(): List[(Flavour, Int)] = {
    val query = (data joinLeft sauceRelation on (_.id === _.chiliId))
                .groupBy (_._1)
                .map(rec => (rec._1, rec._2.length))
                .sortBy(_._2.desc)

    (for (s <- Await.result(db.run(query.result), Duration.Inf)) yield s).toList
  }
  
  def byId(id: Int): Option[Flavour] = {
    val flavours = for (s <- Await.result(db.run(data.filter(_.id === id).result), Duration.Inf)) yield s
    if (flavours.isEmpty) None
    else Some(flavours.head)
  }
  
}
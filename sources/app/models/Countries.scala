package models

import slick.driver.MySQLDriver.api._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._


case class Country(name: String, id: Option[Int] = None)

class Countries(tag: Tag) extends Table[Country](tag, "Pays") {
  def id = column[Int]("idPays", O.PrimaryKey, O.AutoInc)
  def name = column[String]("nom")
  
  def * = (name, id.?) <> ((Country.apply _).tupled, Country.unapply)
}

object Countries {
  private val db = Database.forConfig("h2mem1")
  lazy val data = TableQuery[Countries]
  
  def insert(name: String) = Await.result(db.run(DBIO.seq(data += Country(name))), Duration.Inf)
  
  def all(): List[Country] = (for (s <- Await.result(db.run(data.result), Duration.Inf)) yield s).toList

  def allWithChiliCount(): List[(Country, Int)] = {
    val query = (data joinLeft Chilies.data on (_.id === _.country))
                .groupBy (_._1)
                .map(rec => (rec._1, rec._2.length))
                .sortBy(_._2.desc)

    (for (s <- Await.result(db.run(query.result), Duration.Inf)) yield s).toList
  }
  
  def byId(id: Int): Option[Country] = {
    val countries = for (s <- Await.result(db.run(data.filter(_.id === id).result), Duration.Inf)) yield s
    if (countries.isEmpty) None
    else Some(countries.head)
  }
  
}
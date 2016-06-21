package models

import slick.driver.MySQLDriver.api._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._


case class Chili(name: String, country: Int, id: Option[Int] = None)

class Chilies(tag: Tag) extends Table[Chili](tag, "Piment") {
  def id = column[Int]("idPiment", O.PrimaryKey, O.AutoInc)
  def name = column[String]("nom")
  def country = column[Int]("idPays")
  
  def * = (name, country, id.?) <> ((Chili.apply _).tupled, Chili.unapply)
}



object Chilies {
  private val db = Database.forConfig("h2mem1")
  lazy val data = TableQuery[Chilies]
  lazy val sauceRelation = TableQuery[SaucesToChilies]
  
  def insert(name: String, country: Int) = Await.result(db.run(DBIO.seq(data += Chili(name, country))), Duration.Inf)
  
  def all(): List[Chili] = (for (s <- Await.result(db.run(data.result), Duration.Inf)) yield s).toList

  def allWithSauceCount(): List[(Chili, Int)] = {
    val query = (data joinLeft sauceRelation on (_.id === _.chiliId))
                .groupBy (_._1)
                .map(rec => (rec._1, rec._2.length))
                .sortBy(_._2.desc)

    (for (s <- Await.result(db.run(query.result), Duration.Inf)) yield s).toList
  }
  
  def byId(id: Int): Option[Chili] = {
    val chilies = for (s <- Await.result(db.run(data.filter(_.id === id).result), Duration.Inf)) yield s
    if (chilies.isEmpty) None
    else Some(chilies.head)
  }
  
}
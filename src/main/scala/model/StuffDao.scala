package model

import jdbc.ScalaJdbc

import java.sql.{Connection, PreparedStatement, Statement}
import scala.collection.mutable.ArrayBuffer

object StuffDao {

  def list(connection: Connection):ArrayBuffer[Stuff] = {
    var listStuff = new ArrayBuffer[Stuff]()
    try{
      val statement:Statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT * FROM stuff ORDER BY id ASC")
      while ( resultSet.next() ) {
        val id = resultSet.getInt("id")
        val name = resultSet.getString("stuff_name")
        val qty = resultSet.getInt("qty")
        val price = resultSet.getDouble("price")
        listStuff.append(new Stuff(id, name, qty, price))
      }
    }catch {
      case e =>e.printStackTrace()
    }
    listStuff
  }

  def addStuff(stuff_name:String, qty:Int, price:Double, connection: Connection):Unit = {
    try {
      val stmt = connection.createStatement
      var sql = "INSERT INTO stuff(stuff_name, qty, price)" + "VALUES ('%s', '%d', '%.2f')"
      sql = String.format(sql, stuff_name, qty, price)
      stmt.executeUpdate(sql)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def deleteStuff(id :Int, connection: Connection):Unit = {
    try {
      val sql = "DELETE FROM stuff WHERE id = ?"
      val statement = connection.prepareStatement(sql)
      statement.setInt(1, id)
      statement.executeUpdate
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def updateStuff(id:Int, name:String, qty:Int, price:Double , connection: Connection):Unit = {
    try {
      val sql = "UPDATE stuff SET stuff_name=?, qty=?, price=? WHERE id=?"
      val stmt = connection.prepareStatement(sql)
      stmt.setString(1, name)
      stmt.setInt(2, qty)
      stmt.setDouble(3, price)
      stmt.setInt(4, id)
      stmt.executeUpdate
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}

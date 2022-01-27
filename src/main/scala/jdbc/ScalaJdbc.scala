package jdbc

import java.sql.{Connection, DriverManager, Statement}

object ScalaJdbc {

  def connect() :Connection = {
    // connect to the database named "mysql" on the localhost
    val driver = "org.postgresql.Driver"
    val url = "jdbc:postgresql://localhost/toko"
    val username = "giovanni"
    val password = "Giovgiov@123"

    // there's probably a better way to do this
    var connection:Connection = null
    try{
      // make the connection
      Class.forName(driver)
      connection  = DriverManager.getConnection(url, username, password)
    }catch {
      case e => e.printStackTrace()
    }
    connection
  }
}

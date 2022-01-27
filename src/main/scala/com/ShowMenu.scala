package com

import model.{Stuff, StuffDao}
import jdbc.ScalaJdbc

import scala.io.StdIn.readLine
import java.sql.SQLException

object ShowMenu {
  @throws[SQLException]
  def showMenu(): Unit = {
    var id: Int = 0
    var name: String = null
    var qty: Int = 0
    var save: String = null
    var idNew: String = null
    var price: Double = .0
    var option: Int = 0
    val connect = ScalaJdbc.connect()
    do {
      {
        println("Database Menu:")
        println("1. Show all data")
        println("2. Add Data")
        println("3. Delete Data")
        println("4. Update Data")
        println("0. Exit")
        println("")
        print("Choose Option: ")
        option = readLine().toInt
        try option match {
          case 1 =>
            val stuffs = StuffDao.list(connect)
            println("Id, Name, Qty, Price : ")
            for (s <- stuffs){
              println( s.id + ", "+ s.stuff_name + ", " + s.qty + ", " + s.price)
            }

          case 2 =>
            print("Stuff name: ")
            name = readLine()
            print("Quantity: ")
            qty = readLine().toInt
            print("Price of the stuff: ")
            price = readLine().toDouble
            print("Save? (Y/N)")
            save = readLine()
            if (save.equalsIgnoreCase("y")) {
              StuffDao.addStuff(name, qty, price, connect)
            }
            println("")

          case 3 =>
            StuffDao.list(connect)
            System.out.print("Choose ID: ")
            id = readLine().toInt
            System.out.print("Delete? (Y/N)")
            save = readLine()
            if (save.equalsIgnoreCase("y")) {
              StuffDao.deleteStuff(id, connect)
            }

          case 4 =>
            System.out.print("Choose ID to update: ")
            id = readLine().toInt
            System.out.print("Stuff name: ")
            name = readLine()
            print("Quantity: ")
            qty = readLine().toInt
            System.out.print("Price of the stuff: ")
            price = readLine().toDouble
            System.out.print("Update? (Y/N)")
            save = readLine()
            if (save.equalsIgnoreCase("y")) {
              StuffDao.updateStuff(id,name, qty, price, connect)
            }
            System.out.println("")

          case 0 =>
            connect.close()
            System.out.println("Thanks for using our database")
            System.exit(0)

          case _ =>
            System.out.println("Your choose is null, choose another!")
        }
        catch {
          case e: Exception =>
            e.printStackTrace()
        }
      }
    } while ( {
      option != 0
    })
  }
}

package org.aogde.pixelgame.core

/**
  * Created by russoul on 14.07.2017.
  */
object Startup extends App {

  val game = new PixelGame

  val thread = new Thread(() => game.start())

  thread.start()

}

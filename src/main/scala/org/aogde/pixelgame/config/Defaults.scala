package org.aogde.pixelgame.config

/**
  * Created by russoul on 14.07.2017.
  */
object Defaults {

  final val initialWindowWidth = 800F
  final val initialWindowHeight = 600F

  final val defaultShaderPath = "src/main/resources/shaders/" //TODO path will not work on shipped game

  final val defaultShaders = Seq("color"->"color", "texture"->"texture", "texture_color"->"texture_color")

}

package org.aogde.pixelgame.render

import java.io.File

import org.aogde.pixelgame.render.shader.{Shader, ShaderUtils}
import russoul.lib.common.utils.{Arr, FileUtils, Utilities}

import scala.collection.{TraversableLike, mutable}
import scala.collection.parallel.immutable

/**
  * Created by russoul on 14.07.2017.
  */
class RenderingEngine {



  object System{

    class ShaderNotFoundException(path: String) extends Exception("Shader not found in path: " + path)
    class ShaderDuplicateNameException(name: String) extends Exception(name)

    private final val EXTENSION_VERTEX = "vert"
    private final val EXTENSION_FRAGMENT = "frag"

    //default in case we add support for custom shaders, loaded from game extensions
    private val defaultShaders = new mutable.HashMap[String, Shader]

    /**
      *
      * @param directory
      * @param names names of shaders to load, without file extension (names, newName)
      */
    def loadDefaultShaders(directory: String, names: Traversable[(String,String)]): Unit ={

      for(name <- names){
        val filePathVert = directory + name._1 + "." + EXTENSION_VERTEX
        val filePathFrag = directory + name._1 + "." + EXTENSION_FRAGMENT
        val fileVert = new File(filePathVert)
        val fileFrag = new File(filePathFrag)
        if(!fileVert.exists()) throw new ShaderNotFoundException(filePathVert)
        if(!fileFrag.exists()) throw new ShaderNotFoundException(filePathFrag)

        val sourceVert = Utilities.loadAsString(filePathVert) //TODO new File(..) is called two times
        val sourceFrag = Utilities.loadAsString(filePathFrag)


        val shader = new Shader(sourceVert, sourceFrag)

        if(defaultShaders.contains(name._2)) throw new ShaderDuplicateNameException(name._2)

        defaultShaders += name._2 -> shader
      }

    }


  }

  object User{

  }

}

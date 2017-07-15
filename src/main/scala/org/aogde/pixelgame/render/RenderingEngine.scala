package org.aogde.pixelgame.render

import java.awt.Window
import java.io.File

import org.aogde.pixelgame.component.WindowInfo
import org.aogde.pixelgame.config.Defaults
import org.aogde.pixelgame.render.definition._
import org.aogde.pixelgame.render.shader.{Shader, ShaderUtils}
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import russoul.lib.common.utils.{Arr, FileUtils, Utilities}
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import russoul.lib.common.Implicits._

import scala.collection.{TraversableLike, mutable}

/**
  * Created by russoul on 14.07.2017.
  */
class RenderingEngine {

  class RenderInfo[Life <: RenderLifetime, Trans <: RenderTransformation](val lifetime: Life, val transformation : Trans, renderer: RendererVertFrag)

  object System{ //TODO make it so only game core game code can access this object, not the user code

    class ShaderNotFoundException(path: String) extends Exception("Shader not found in path: " + path)
    class ShaderDuplicateNameException(name: String) extends Exception(name)

    private final val EXTENSION_VERTEX = "vert"
    private final val EXTENSION_FRAGMENT = "frag"

    //default in case we add support for custom shaders, loaded from game extensions
    private val defaultShaders = new mutable.HashMap[String, Shader]



    object Init{ //TODO check for proper initialization before any rendering
      /**
        *
        * @param directory
        * @param names names of shaders to load, without file extension (names, newNames)
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

    /*
      object RenderMode extends Enumeration {
        type Name = Value
        val Wireframe, Full = Value
      }
    */

    //for now writing renderers and interfaces supposing that we have only two shader procedures available : vert and frag
    object Render{


      var curRenderID = Long.MinValue //TODO that is not enough, refresh it somehow


      //these renderers need to be updated each frame, fully cleared after each framebuffer swap
      //those match LifetimeOneDraw lifetime
      val lifetimeOneDrawWorldRenderers = new mutable.HashMap[RenderID, RendererVertFrag]
      val lifetimeOneDrawUIRenderers = new mutable.HashMap[RenderID, RendererVertFrag]

      def draw(windowInfo: WindowInfo): Unit ={
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) // clear the framebuffer

        glClearColor(Defaults.initialBackgroundColor._1, Defaults.initialBackgroundColor._2, Defaults.initialBackgroundColor._3, 1)

        glfwSwapBuffers(windowInfo.getID()) // swap the color buffers
      }

      private def drawUI(windowInfo: WindowInfo): Unit ={

      }
    }


  }

  object User{

    class UnsupportedRenderLifetimeException(lifetime: RenderLifetime) extends Exception("Unsupported render lifetime : " + lifetime.name())
    class UnsupportedRenderTransformationException(tr : RenderTransformation) extends Exception("Unsupported render transformation : " + tr.name())

    //TODO
    //def push(lifetime, whereToRender, renderer) : ID = ???
    //lifetime defines when the renderer will be destroyed (after one frame draw or after a some event)
    //whereToRender defines some basic transformations like camera and type or projection before drawing
    //renderer is all other information required to draw a shape

    //multithread-unsafe
    def push[Life <: RenderLifetime, Trans <: RenderTransformation](lifetime: Life, transformation: Trans, renderer: RendererVertFrag): RenderID ={
      lifetime match{
        case LifetimeOneDraw =>

          var dest = None : Option[mutable.HashMap[RenderID, RendererVertFrag]]

          transformation match{
            case TransformationUI =>
              dest = Some(System.Render.lifetimeOneDrawUIRenderers)
            case TransformationWorld =>
              dest = Some(System.Render.lifetimeOneDrawWorldRenderers)
            case unsupported => throw new UnsupportedRenderTransformationException(unsupported)
          }

          val id = System.Render.curRenderID
          val ret = new RenderID(id)
          System.Render.curRenderID += 1
          dest.get += (ret -> renderer)
          ret

        case unsupported => throw new UnsupportedRenderLifetimeException(unsupported)
      }
    }

    /*object EachFrame{ //when
      object UI{ //where
        object Wireframe{ //how
          object RegularConvexPolygon{ //what

          }
        }
      }
    }*/
  }

}

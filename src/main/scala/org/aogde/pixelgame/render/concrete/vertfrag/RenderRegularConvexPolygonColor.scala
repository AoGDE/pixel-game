package org.aogde.pixelgame.render.concrete.vertfrag

import org.aogde.pixelgame.render.definition.{RendererVertFrag, RendererVertFragDefault}
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL11._
import russoul.lib.common._
import russoul.lib.common.Implicits._
import russoul.lib.common.math.geometry.complex.RegularConvexPolygon2
import shapeless.Nat

/**
  * Created by russoul on 15.07.2017.
  */
class RenderRegularConvexPolygonColor extends RendererVertFragDefault{
  override protected val renderMode: Int = GL_TRIANGLES
  override protected var vertexSize: Int = 6 //vec3 as pos with depth + vec3 as color


  def add[N <: Nat](shape: RegularConvexPolygon2[Float,N], zLevel: Float, color: Float3) : Unit = {
    val angle = 2*Math.PI/shape.getN()

    for(i <- 0 until shape.getN()){
      val x = Math.cos(angle*i).toFloat
      val y = Math.sin(angle*i).toFloat

      val l = Float2(x,y)

      val vertex = l*shape.rad + shape.center

      vertexPool += vertex.x
      vertexPool += vertex.y
      vertexPool += zLevel

      vertexPool += color.x
      vertexPool += color.y
      vertexPool += color.z
    }


    for(i <- 0 until shape.getN() - 1){
      indexPool += i + vertexCount
      indexPool += i + 1 + vertexCount
    }

    indexPool += vertexCount + shape.getN() - 1
    indexPool += vertexCount

    vertexCount += shape.getN()
  }

  override def setAttributePointers(): Unit = {
    glVertexAttribPointer(0, 3, GL_FLOAT, false, vertexSize * 4, 0)
    glEnableVertexAttribArray(0)

    glVertexAttribPointer(1, 3, GL_FLOAT, false, vertexSize * 4, 3 * 4)
    glEnableVertexAttribArray(1)
  }

  override def getShaderName(): String = "color"
}

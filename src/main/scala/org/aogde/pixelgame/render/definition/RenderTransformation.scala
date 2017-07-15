package org.aogde.pixelgame.render.definition

/**
  * Created by russoul on 15.07.2017.
  */
trait RenderTransformation{
  def name():String
}

object TransformationWorld extends RenderTransformation{
  override def name(): String = "TransformationWorld"
}
object TransformationUI extends RenderTransformation{
  override def name(): String = "TransformationUI"
}

package org.aogde.pixelgame.core

import java.nio.IntBuffer

import org.aogde.pixelgame.component.WindowInfo
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWErrorCallback, GLFWVidMode}
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, glClear, glClearColor}
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import Russoul.lib.common._

/**
  * Created by russoul on 14.07.2017.
  */
class PixelGame{

  private val windowInfo = new WindowInfo(Defaults.initialWindowWidth, Defaults.initialWindowHeight)

  def start(): Unit ={
    println("Using LWJGL " + Version.getVersion)

    init()
    run()

    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(windowInfo.getID())
    glfwDestroyWindow(windowInfo.getID())

    // Terminate GLFW and free the error callback
    glfwTerminate()
    glfwSetErrorCallback(null).free()
  }

  private def init(): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit) throw new IllegalStateException("Unable to initialize GLFW")

    // Configure GLFW
    glfwDefaultWindowHints() // optional, the current window hints are already the default

    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation

    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable


    // Create the window
    windowInfo.setID(glfwCreateWindow(300, 300, "Hello World!", NULL, NULL))
    if (windowInfo.getID() == NULL) throw new RuntimeException("Failed to create the GLFW window")

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(windowInfo.getID(), (window: Long, key: Int, scancode: Int, action: Int, mods: Int) => {
      def callback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) = {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true) // We will detect this in the rendering loop
      }

      callback(window, key, scancode, action, mods)
    })

    auto(stackPush){ stack =>
      val pWidth = stack.mallocInt(1)
      // int*
      val pHeight = stack.mallocInt(1)
      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(windowInfo.getID(), pWidth, pHeight)
      // Get the resolution of the primary monitor
      val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor)
      // Center the window
      glfwSetWindowPos(windowInfo.getID(), (vidmode.width - pWidth.get(0)) / 2, (vidmode.height - pHeight.get(0)) / 2)
    }


    // Make the OpenGL context current
    glfwMakeContextCurrent(windowInfo.getID())
    // Enable v-sync
    glfwSwapInterval(1)

    // Make the window visible
    glfwShowWindow(windowInfo.getID())
  }

  private def run(): Unit ={
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities

    // Set the clear color
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

    // Run the rendering loop until the user has attempted to close
    // the window or has pressed the ESCAPE key.
    while ( {
      !glfwWindowShouldClose(windowInfo.getID())
    }) {
      events()
      update()
      draw()
    }
  }

  def update(): Unit =
  {

  }

  def events(): Unit ={
    // Poll for window events. The key callback above will only be
    // invoked during this call.
    glfwPollEvents()
  }

  def draw(): Unit ={
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) // clear the framebuffer



    glfwSwapBuffers(windowInfo.getID()) // swap the color buffers
  }
}
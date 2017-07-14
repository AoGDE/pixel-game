import org.omg.SendingContext.RunTime

name := "pixel-game"
version := "0.0.1"
scalaVersion := "2.12.2"
organizationName := "AoGDE"

val lwjglOrganization = "org.lwjgl"
val lwjglVersion = "3.1.2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"



//DEPENDECIES-----------------------------------------------------------------
//LWJGL 3
libraryDependencies += lwjglOrganization % "lwjgl"          % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl"          % lwjglVersion classifier "natives-windows"
libraryDependencies += lwjglOrganization % "lwjgl-assimp"   % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-assimp"   % lwjglVersion classifier "natives-windows"
libraryDependencies += lwjglOrganization % "lwjgl-bgfx"     % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-egl"      % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-glfw"     % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-glfw"     % lwjglVersion classifier "natives-windows"
libraryDependencies += lwjglOrganization % "lwjgl-jawt"     % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-jemalloc" % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-jemalloc" % lwjglVersion classifier "natives-windows"
libraryDependencies += lwjglOrganization % "lwjgl-lmdb"     % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-nanovg"   % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-nfd"      % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-nuklear"  % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-openal"   % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-openal"   % lwjglVersion classifier "natives-windows"
libraryDependencies += lwjglOrganization % "lwjgl-opengl"   % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-opengl"   % lwjglVersion classifier "natives-windows"
libraryDependencies += lwjglOrganization % "lwjgl-opencl"   % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-sse"      % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-par"      % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-stb"      % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-xxhash"   % lwjglVersion
libraryDependencies += lwjglOrganization % "lwjgl-yoga"     % lwjglVersion
//...

//UniScalaLibrary
libraryDependencies += "org.russoul" %% "uniscalalib" % "0.0.1"
//...

//-----------------------------------------------------------------------------------


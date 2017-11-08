import sbt.Keys.libraryDependencies

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name         := "petshop-scala-1",
    organization := "com.github.scova0731",
    scalaVersion := "2.12.4",
    version      := "0.1.0-SNAPSHOT",

    libraryDependencies += jdbc,
    libraryDependencies += guice,

    libraryDependencies += "com.h2database" % "h2" % "1.4.192",
    libraryDependencies += "org.mybatis" % "mybatis" % "3.4.5",
    libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.10",
    libraryDependencies += "net.sourceforge.stripes" % "stripes" % "1.6.0", // TODO to be moved
    libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1", // TODO to be moved
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,

    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources",
      excludeFilter in Compile in unmanagedResources := "*.sql"
  )

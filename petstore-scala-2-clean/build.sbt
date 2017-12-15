import sbt.Keys.libraryDependencies

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name         := "petstore4s-step2",
    organization := "com.github.scova0731",
    scalaVersion := "2.12.4",
    version      := "0.1.0-SNAPSHOT",

    libraryDependencies += jdbc excludeAll
      ExclusionRule(organization = "com.jolbox", name = "bonecp"),
    libraryDependencies += guice,
    libraryDependencies += ehcache,
    libraryDependencies += "com.h2database" % "h2" % "1.4.192",
    libraryDependencies += "org.mybatis" % "mybatis" % "3.4.5",
    libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.10" excludeAll (
      ExclusionRule(organization = "com.jolbox", name = "bonecp"),
      ExclusionRule(organization = "com.google.inject", name = "guice")
    ),
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  )
  .aggregate(moduleGateway, moduleInteraction)
  .dependsOn(moduleGateway, moduleInteraction)


lazy val moduleGateway = (project in file("./module-gateway"))
    .settings(
      name         := "clean-architecture-gateway",

      libraryDependencies += "com.google.inject" % "guice" % "4.1.0",
      libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
    )
    .aggregate(moduleInteraction)
    .dependsOn(moduleInteraction)


lazy val moduleInteraction=
  (project in file("./module-interaction"))
    .settings(
      name         := "clean-architecture-interaction",

      libraryDependencies += "com.google.inject" % "guice" % "4.1.0",
      libraryDependencies += "net.sourceforge.stripes" % "stripes" % "1.6.0",
      libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
    )

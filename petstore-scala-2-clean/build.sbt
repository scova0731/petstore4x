import sbt.Keys.libraryDependencies

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name         := "petstore4s-step2",
    organization := "com.github.scova0731.petstore4s.step2",
    scalaVersion := "2.12.4",
    version      := "0.1.0-SNAPSHOT",

    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources",
    excludeFilter in Compile in unmanagedResources := "*.sql"
  )
  .aggregate(moduleGateway, moduleWeb)
  .dependsOn(moduleGateway, moduleWeb)


/**
  * Core
  */
lazy val moduleCore = (project in file("./module-core"))
  .settings(
    name         := "petstore-step2-core",

    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
  )

/**
  * Gateway
  */
lazy val moduleGateway = (project in file("./module-gateway"))
  .settings(
    name         := "petstore4s-step2-gateway",

    libraryDependencies += "com.typesafe.play" %% "play-jdbc-api" % "2.6.6" % "provided",
    libraryDependencies += "com.google.inject" % "guice" % "4.1.0",
    libraryDependencies += "com.h2database" % "h2" % "1.4.192",
    libraryDependencies += "org.mybatis" % "mybatis" % "3.4.5",
    libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.10" excludeAll (
      ExclusionRule(organization = "com.jolbox", name = "bonecp"),
      ExclusionRule(organization = "com.google.inject", name = "guice")
    ),
    libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
  )
  .aggregate(moduleCore)
  .dependsOn(moduleCore)


/**
  * Interaction
  */
lazy val moduleInteraction = (project in file("./module-interaction"))
  .settings(
    name         := "petstore4s-step2-interaction",

    libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
  )
  .aggregate(moduleCore)
  .dependsOn(moduleCore)


/**
  * Web
  */
lazy val moduleWeb = (project in file("./module-web"))
  .enablePlugins(PlayScala)
  .settings(
    name         := "petstore4s-step2-web",

    libraryDependencies += jdbc excludeAll
      ExclusionRule(organization = "com.jolbox", name = "bonecp"),
    libraryDependencies += guice,
    libraryDependencies += ehcache,
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  )
  .aggregate(moduleInteraction)
  .dependsOn(moduleInteraction)

package coursier.internal

import coursier.Repositories
import coursier.core.Repository
import coursier.ivy.IvyRepository
import coursier.maven.MavenRepository

object SharedRepositoryParser {

  def repository(s: String): Either[String, Repository] =
    if (s == "central")
      Right(Repositories.central)
    else if (s.startsWith("sonatype:"))
      Right(Repositories.sonatype(s.stripPrefix("sonatype:")))
    else if (s.startsWith("bintray:")) {
      val s0 = s.stripPrefix("bintray:")
      val id =
        if (s.contains("/")) s0
        else s0 + "/maven"

      Right(Repositories.bintray(id))
    }
    else if (s.startsWith("bintray-ivy:"))
      Right(Repositories.bintrayIvy(s.stripPrefix("bintray-ivy:")))
    else if (s.startsWith("typesafe:ivy-"))
      Right(Repositories.typesafeIvy(s.stripPrefix("typesafe:ivy-")))
    else if (s.startsWith("typesafe:"))
      Right(Repositories.typesafe(s.stripPrefix("typesafe:")))
    else if (s.startsWith("sbt-maven:"))
      Right(Repositories.sbtMaven(s.stripPrefix("sbt-maven:")))
    else if (s.startsWith("sbt-plugin:"))
      Right(Repositories.sbtPlugin(s.stripPrefix("sbt-plugin:")))
    else if (s == "scala-integration" || s == "scala-nightlies")
      Right(Repositories.scalaIntegration)
    else if (s.startsWith("ivy:")) {
      val s0     = s.stripPrefix("ivy:")
      val sepIdx = s0.indexOf('|')
      if (sepIdx < 0)
        IvyRepository.parse(s0)
      else {
        val mainPart     = s0.substring(0, sepIdx)
        val metadataPart = s0.substring(sepIdx + 1)
        IvyRepository.parse(mainPart, Some(metadataPart))
      }
    }
    else if (s == "jitpack")
      Right(Repositories.jitpack)
    else if (s == "clojars")
      Right(Repositories.clojars)
    else if (s == "jcenter")
      Right(Repositories.jcenter)
    else if (s == "google")
      Right(Repositories.google)
    else if (s == "gcs")
      Right(Repositories.centralGcs)
    else if (s == "gcs-eu")
      Right(Repositories.centralGcsEu)
    else if (s == "gcs-asia")
      Right(Repositories.centralGcsAsia)
    else if (s.startsWith("apache:"))
      Right(Repositories.apache(s.stripPrefix("apache:")))
    else
      Right(MavenRepository(s))

}

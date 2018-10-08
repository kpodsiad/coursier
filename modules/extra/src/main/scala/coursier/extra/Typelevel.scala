package coursier.extra

import coursier.{Dependency, Module, organizationString}

object Typelevel {

  val mainLineOrg = org"org.scala-lang"
  val typelevelOrg = org"org.typelevel"

  val modules = Set(
    "scala-compiler",
    "scala-library",
    "scala-library-all",
    "scala-reflect",
    "scalap"
    // any other?
  )

  def swap(module: Module): Module =
    if (module.organization == mainLineOrg && modules(module.name) && module.attributes.isEmpty)
      module.copy(
        organization = typelevelOrg
      )
    else
      module

  def swap(dependency: Dependency): Dependency =
    dependency.copy(
      module = swap(dependency.module)
    )

}
# NyaaSi-API
Java API implementation for https://nyaa.si/ and https://sukebei.nyaa.si/

Since this api parses webpages, it might break any time.

# Installation
The recommended way to use this library is to add the github repository as a project in your build.sbt and let your project depend on it.
Sbt builds the library from source and will apply all commits in the repository.
``` sbt
lazy val nyaaSi = RootProject(uri("https://github.com/kaysubs/NyaaSi-API"))
dependsOn(nyaaSi)
```
Of course you may also use a different build tool that support that feature or manually compile this library and its dependencies.

# Usage
The entry point of this API is the `NyaaSiApi.getNyaa` method for access to https://nyaa.si/ and `NyaaSiApi.getSukebei` for https://sukebei.nyaa.si/

For more example usages you may have a look at the `de.kaysubs.tracker.nyaasi.examples` package.

# NyaaSi-API
Java API implementation for https://nyaa.si/ and https://sukebei.nyaa.si/

Since this api parses webpages, it might break any time.

# Installation
One way to use this library is to inculde it through jitpack.
How to use it with your build tool is explained at [their page](https://jitpack.io/#aki-ks/NyaaSi-API/master).

If you're building with sbt, you may add this library as a remote project in your `build.sbt` and let your project depend on it.
Sbt builds the library from source and will apply all new commits to the repository.
``` sbt
lazy val nyaaSi = RootProject(uri("https://github.com/kaysubs/NyaaSi-API"))
dependsOn(nyaaSi)
```

# Usage
The entry point of this API is the `NyaaSiApi.getNyaa` method for access to https://nyaa.si/ and `NyaaSiApi.getSukebei` for https://sukebei.nyaa.si/

For more example usages you may have a look at the `de.kaysubs.tracker.nyaasi.examples` package.

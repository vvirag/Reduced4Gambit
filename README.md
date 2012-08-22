Reduced4Gambit
==============

Simple application to demonstrate the usage of the reduced form (based on greenvirag/gte/lib-algo)

I. PREREQUISITES

1. Set up your greenvirag/gte repository to build lib-algo.jar

Follow this: https://github.com/greenvirag/gte/blob/master/README

2. Go to exe directory, and edit your build.xml file.

Set up the correct path to Reduced4Gambit and also for the project from the 1st step (where the lib-algo.jar will be built).


II. BUILD

Open terminal:

= Bulding lib-algo.jar =

$ cd <greenvirag/gte project root directory>

$ ant compile-core clean

$ ant compile-core

$ ant package-core

= Building Reduced4Gambit (which needs an available lib-algo.jar) =

$ cd <greenvirag/Reduced4Gambit project root directory>/exe

$ ant

When ant is running, you should see the corresponding build.xml script being executed.

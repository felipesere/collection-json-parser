collection-json-parser
======================

Collection-JSON-parser is a small library for reading and writing JSON in collection+json (from @mamund) format.
It was born because other Cj libraries written for Java were inactive for about a year.

The library provides a Java model for `Collections`, `Queries`, `Tempaltes`, `Items` and Data from Cj.
Futhermore it provives a `Serializer` which converts any of the above mentioned classes to and from JSON.

For people building a client that uses Cj, the library provides a `CjClient` which takes care of building requests
and parsing responses. Users will need to implement a small `HTTPClient` which is used by the `CjClient` to handle
communication.

To make building of `Collections` easier, a set of builders are provided.

Finally, to convert between `Items` in a `Collection` and your domain model, the library makes use of `Transformers`.


Getting started
-----------------

To use the library in your project you need to clone it

```git clone https://github.com/felipesere/collection-json-parser.git```

and then build it with Maven

```maven clean install``


then add it as a dependency to your Maven project using the following coordinates

```
<dependency>
   <groupId>de.fesere.hypermedia</groupId>
   <artifactId>collection-json-parser</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Then you can proceed to create your own collections of read them from Strings.

Build status:
-------------

| Branch | Status |
| ------ | ------ |
|   Development | [![Build Status](https://travis-ci.org/felipesere/collection-json-parser.png?branch=develop)](https://travis-ci.org/felipesere/collection-json-parser) |
| Master:     | [![Build Status](https://travis-ci.org/felipesere/collection-json-parser.png?branch=master)](https://travis-ci.org/felipesere/collection-json-parser)    |
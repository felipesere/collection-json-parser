collection-json-parser
======================

Collection-JSON-parser is a small library for reading and writing JSON in [collection+json](https://github.com/mamund/collection-json) (from @mamund) format.
It was born because I wanted to build a prototype for the office and was in need of pet-project.

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


Using `collection-json-parser` to read
---------------------------------------

The tests in `/src/main/test/java` mostly show how to use the library.

In a gist, the usage pattern is the following:
If you receive a `String` containing a `Collection` in JSON, you can serialize it as such:

````
String json = "...";
Serializer serializer = new Serializer();

Collection collection = serializer.deserialize(json, Collection.class);
````

If you then want to extract the `Items` and convert them to some domain object, e.g. `Foo`,
you have to implement the `ReadTransformer<T>` interface and pass it to the convert method:

````
FooTransformer fooTransformer = new FooTransfomer();
List<Foo> foos = collection.transform(fooTransformer);
````

The implementation of `FooTransformer` get each `Item` of the `Collection` one at a time.
It should use the methods on `Item` to extract values.
The methods are:

*  `getString(String name)` to get a `String` value
*  `getInt(Stirng name)`to get an `int`
*  `getDouble(String name)` to get a `double`

These methods throw an `ElementNotFoundException` if no element named `name` is found
and both `getInt` and `getDouble` throw a `MalformedDataValueException` if the value
can not be properly converted.


Using `collection-json-parser` to write
-----------------------------------------


Build status:
-------------

| Branch | Status |
| ------ | ------ |
|   Development | [![Build Status](https://travis-ci.org/felipesere/collection-json-parser.png?branch=develop)](https://travis-ci.org/felipesere/collection-json-parser) |
| Master:     | [![Build Status](https://travis-ci.org/felipesere/collection-json-parser.png?branch=master)](https://travis-ci.org/felipesere/collection-json-parser)    |
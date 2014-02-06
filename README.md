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

`git clone https://github.com/felipesere/collection-json-parser.git`

and then build it with Maven

`maven clean install`


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

```Java
String json = "...";
Serializer serializer = new Serializer();

Collection collection = serializer.deserialize(json, Collection.class);
```

If you then want to extract the `Items` and convert them to some domain object, e.g. `Foo`,
you have to implement the `ReadTransformer<T>` interface and pass it to the convert method:

```Java
FooTransformer fooTransformer = new FooTransfomer();
List<Foo> foos = collection.transform(fooTransformer);
```

The implementation of `FooTransformer` get each `Item` of the `Collection` one at a time.
It should use the methods on `Item` to extract values.
The methods are:

*  `getString(String name)` to get a `String` value
*  `getInt(Stirng name)`to get an `int`
*  `getDouble(String name)` to get a `double`
*  `getBoolean(String name)`to get a `boolean`

These methods throw an `ElementNotFoundException` if no element named `name` is found
and both `getInt` and `getDouble` throw a `MalformedDataValueException` if the value
can not be properly converted.


Using `collection-json-parser` to write
-----------------------------------------

Writing a `Collection`is fairly easy.
Simply use the differnet builders in `de.fesere.hypermedia.cj.model.builder` to construct the differnet objects.

For example, if you want to create a `Collection` with a single `Item` and two `Links`, you could the follwing

```Java
CollectionBuilder collectionBuilder = new CollectionBuilder(URI.create("http://example.com"));
collectionBuilder.getLinkBuilder().addLink("documentation","/documentation/v1")
                                  .addLink("questions", URI.create("http://stackoverflow.com")).build();

ItemBuilder itemBuilder = new ItemBuilder(URI.create("http;//example.com/item/1"));
itemBuilder.addData(new StringDataEntry("name", "Bob", "Users first name"));
Collection collection = collectionBuilder.addItem(itemBuilder.build()).build();

Serializer serializer = new Serializer();
System.out.println(serializer.serialize(collection));
```

The `DataEntry` can be any of

* `StringDataEntry` to add `String` to the data of an entity/template
* `NumberDataEntry` to add anything that implements the Java `Number`interface, such as `int` and `double`
* `BooleanDataEntry` to add a `boolean` to the item/template


which would result in

```JSON
{
    "collection": {
        "version": "1.0",
        "href": "http://example.com",
        "links": [
            {
                "rel": "documentation",
                "href": "http://example.com/documentation/v1"
            },
            {
                "rel": "questions",
                "href": "http://stackoverflow.com"
            }
        ],
        "items": [
            {
                "href": "http;//example.com/item/1",
                "data": [
                    {
                        "name": "name",
                        "value": "Bob",
                        "prompt": "Users first name"
                    },
                    {
                        "name": "age",
                        "value": 24,
                        "prompt": "Users age"
                    },
                    {
                        "name": "height",
                        "value": 0.00192,
                        "prompt": "Users height in km"
                    },
                    {
                        "name": "payed",
                        "value": false,
                        "prompt": "User payed fee"
                    }
                ]
            }
        ]
    }
}
```


Contributing:
-------------

I try to follow the [Git flow](http://nvie.com/posts/a-successful-git-branching-model/) model by
having a *master* and a *develop* branch.
Please branch from the *develop* branch and send me pull requests.

**Currently there is no differnce between master and develop, but this will change once there are stable releases**.



Build status:
-------------

| Branch | Status |
| ------ | ------ |
|   Development | [![Build Status](https://travis-ci.org/felipesere/collection-json-parser.png?branch=develop)](https://travis-ci.org/felipesere/collection-json-parser) |
| Master:     | [![Build Status](https://travis-ci.org/felipesere/collection-json-parser.png?branch=master)](https://travis-ci.org/felipesere/collection-json-parser)    |
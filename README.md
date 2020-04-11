# Library Demo #

## Build & Run ##

```sh
$ cd library-demo
$ sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

API is defined in [/src/main/resources/openapi.yaml](https://github.com/Brimborion/clean-scalatra/blob/master/src/main/resources/openapi.yaml).

You can use Postman to explore the API importing `LibraryDemo.postman_collection.json` file.

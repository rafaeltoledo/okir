OkHttp Idling Resource
======================

A helper class that implements both an Espresso `IdlingResource` and an OkHttp 2 and 3 `Interceptor`s.

[![Build Status](https://api.travis-ci.org/rafaeltoledo/okir.svg)](https://travis-ci.org/rafaeltoledo/okir)  [![Android Arsenal](http://img.shields.io/badge/Android%20Arsenal-OkHttp%20Idling%20Resource-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3897)

Usage
-----

Just create an instance of the desired implementation,

```java
@RunWith(AndroidJunit.class)
public class MyEspressoTest {

    private OkHttp3IdlingResource okir = new OkHttp3IdlingResource();
}
```

register it on Espresso,

```java
    @Before
    public void setUp() {
        Espresso.registerIdlingResources(okir);
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(okir);
    }
```

add it to your `OkHttpClient` and replace it on your production code.

```java
    ...
    @Before
    public void setUp() {
        Espresso.registerIdlingResources(okir);
        OkHttpClient client = ApiCaller.getClient().newBuilder()
                .addInterceptor(okir)
                .build();
        ApiCaller.setClient(client);
    }
```

For a working example, check the `sample` folder.

It's recommended to annotate your setter method with `@VisibleForTesting` to avoid the accidental change
on production code. Another way to do this is through the Java's reflection API.

Customization
-------------

For some cases (e.g., using the OkHttpClient instance for both Retrofit and Picasso), you only want
to wait for some URLs. If you need this behavior, just pass the URLs you want to wait in the constructor.

```java
// I just want to wait for requests to Github's API.
OkHttp3IdlingResource okir = new OkHttp3IdlingResource("https://api.github.com");
```

*It's highly recommended to mock your requests when testing. A great solution is to use Square's [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)*

Download
--------

The library is available on JCenter. Add the library as an Android Test dependency:

```groovy
dependencies {
    ...
    androidTestCompile 'net.rafaeltoledo.okir:library:0.0.1@aar'
}
```

License
-------

    Copyright 2016 Rafael Toledo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



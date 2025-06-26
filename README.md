````markdown
# FHttp

A lightweight, experimental Java HTTP server library

## 🔥 Features

- HTTP/1.1 support with pipelining
- Fully asynchronous
- No object aggregation (no `FullHttpRequest`)
- Multicore by default
- Ideal for writing high performance HTTP services

## 📦 Installation

This library is hosted on [JitPack](https://jitpack.io/#fuji-184/FHttp).

### 1. Add the JitPack repository to your project's `pom.xml`:

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
````

### 2. Add the `FHttp` dependency:

```xml
<dependency>
  <groupId>com.github.fuji-184</groupId>
  <artifactId>fhttp</artifactId>
  <version>0.0.1</version>
</dependency>
```

---

## 🚀 Getting Started

Here is a minimal example to create a simple HTTP server:

```java
import fuji.fhttp.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router.Builder()
            .get("/", (req, res) -> res.send("Hello from fhttp!"))
            .build();

        new Server(8080, router).start();
    }
}
```

# HSLDataConverter

[![](https://jitpack.io/v/net.william278/HSLDataConverter.svg)](https://jitpack.io/#net.william278/HSLDataConverter)

A simple library to convert serialized legacy HuskSync (v1.x) data into Spigot API data.

This library is only for use in upgrading or converting HuskSync v1.x data in a portable manner and is utilised in
HuskSync v2.x for data migration.

## Including in your project

Builds are available on [JitPack](https://jitpack.io/#net.william278/HSLDataConverter/). Include them in your pom.xml or
build.gradle file as per below.

### Maven

```xml
<repository>
    <id>jitpack</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml

<dependency>
    <groupId>net.william278</groupId>
    <artifactId>HSLDataConverter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'net.william278:HSLDataConverter:1.0.0'
}
```

## Building

To build, simply run `./gradlew clean build` in the source root. Legacy HuskSync serialized classes are bundled and this
resource does not depend on the HuskSync plugin itself.
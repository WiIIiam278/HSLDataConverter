# HSLDataConverter
A simple library to convert serialized legacy HuskSync (v1.x) data into Spigot API data.

This library is only for use in upgrading or converting HuskSync v1.x data in a portable manner and is utilised in HuskSync v2.x for data migration.

## Building
To build, simply run `./gradlew clean build` in the source root. Legacy HuskSync serialized classes are bundled and this resource does not depend on the HuskSync plugin itself.
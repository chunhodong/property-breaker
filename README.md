## Property Beaker
[![GitHub license](https://img.shields.io/badge/License-GPL%203.0-blue.svg)](https://github.com/chunhodong/property-breaker/blob/master/License)

Property Breaker blocks the specified property value in the application.yml or application.properties file.
By default, a property is provided that blocks hibernate.ddl-auto: create.
Additionally, it provides a property that can block properties written to the application.yml or application.properties file.


## How to start
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}


dependencies {
   implementation 'com.github.chunhodong:property-breaker:0.0.4'
}
```


## How to use
```yaml
chunhodong:
  property-breaker:
      hibernate-ddlauto-deactive: true
```
If there is a spring.jpa.hibernate.ddl-auto:create property in application.yml or application.properties file, an exception is raised after starting Spring Boot.

```yaml
chunhodong:
  property-breaker:
      general-property-deactive:
              key: server.port
              value: 3001

```
If you want to block other property values besides spring.jpa.hibernate.ddl-auto, you can do it.

## Effect of use
A property-breaker prevents unexpected side effects.
For example, if hibernate.ddl-auto:create in application.yml or application.perperties file on the production server, all database tables serving customers can be deleted.

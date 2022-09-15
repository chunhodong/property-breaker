## Property Beaker
Property Breaker Blocks the specified property value in the application.yml or application.properties file.
By default, a property is provided that blocks hibernate.ddl-auto: create.
Additionally, it provides a property that can block properties written to the application.yml or application.properties file.


## How to use
```yaml
chunhdong:
  property-breaker:
      hibernate-ddlauto-deactive: true
```
If there is a spring.jpa.hibernate.ddl-auto:create property in application.yml or application.properties file, an exception is raised after starting Spring Boot.

```yaml
chunhdong:
  property-breaker:
      general-property-deactive:
              key: server.port
              value: 3001

```

If you want to block other property values besides spring.hibernate.ddl-auto, you can block it.

## Effect of use
A property-breaker prevents unexpected side effects.
For example, if hibernate.ddl-auto:create in application.yml or application.perperties file on the production server, all database tables serving customers can be deleted.

# Code-Test

Consumer web application that demonstrates and tests the Randy Framework. Connects to a MySQL database via a Spring-managed service and repository layer, while routing and view rendering are handled entirely by the custom framework.

---

## Responsibility split

```
Framework owns:               Spring owns:
─────────────────────         ────────────────────────
@Controller scanning          @Service instantiation
@UrlMapping routing           @Repository instantiation
FrontControllerServlet        DataSource / JdbcTemplate
ModelAndView rendering        Dependency injection (@Autowired)
JSP / HTML view dispatch      Bean lifecycle
```

The controller is instantiated by the framework via reflection. Spring never touches it. The controller receives the `ApplicationContext` as a method parameter and calls `getBean()` to reach Spring-managed services.

---

## Project structure

```
src/main/java/com/example/
├── controller/
│   └── TestController.java   — @Controller, routes defined with @UrlMapping
├── entity/
│   └── Livre.java            — plain Java object (id, titre, auteur) with getters/setters
├── service/
│   └── LivreService.java     — @Service, calls the repository
└── repository/
    └── LivreRepository.java  — @Repository, JdbcTemplate queries against MySQL

src/main/webapp/
├── WEB-INF/
│   ├── web.xml               — servlet, listeners, Spring config location
│   ├── applicationContext.xml — Spring beans: datasource, component-scan
│   └── views/
│       └── test.jsp          — JSP view rendered by the framework
```

---

## Controller example

```java
@Controller
public class TestController {

    @UrlMapping(value = "/test", method = "GET")
    public ModelAndView afficherMessages(ApplicationContext ctx) {
        LivreService service = ctx.getBean(LivreService.class);
        List<Livre> livres = service.getTousLesLivres();

        ModelAndView mv = new ModelAndView();
        mv.setView("test");
        mv.setAttribute("livres", livres.toArray());
        return mv;
    }
}
```

The method parameter `ApplicationContext ctx` is the signal to the framework to inject the Spring container. No `@Autowired`, no Spring annotation on the controller itself.

---

## Spring configuration

**`web.xml`** — wires Spring's listener before the framework listener:
```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/applicationContext.xml</param-value>
</context-param>

<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
<listener>
    <listener-class>randy.framework.listener.InitListener</listener-class>
</listener>
```

**`applicationContext.xml`** — datasource and component scan:
```xml
<context:component-scan base-package="com.example.service, com.example.repository"/>

<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/bibliotheque"/>
    <property name="username" value="root"/>
    <property name="password" value="yourpassword"/>
</bean>
```

---

## Build and deploy

```bash
# 1. Install the framework into your local Maven repo first
cd Framework/
mvn clean install

# 2. Build and deploy
cd Code-Test/code/
./deploy.sh # If it fails, grant execute permissions

```

Always re-run `mvn install` on the Framework whenever you change it, before rebuilding Code-Test. Otherwise Code-Test picks up the old JAR from `~/.m2`.
# 全栈自动化

## 简述

全栈是back-end和front-end的解耦式开发。全栈自动化指的是将后端和前端的接口使用代码自动化生成，具体实现是字符串拼接，文件创建等。

目前从比较熟悉的语言来看，使用前端语言Java script和Type script。后端语言Java。

目前从比较熟悉的框架来看，使用前端框架Angular。后端框架Spring boot。

版本号一览表

```
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
mysql		v5.7.29
java		v1.8.0_251
springboot	v2.3.1.RELEASE
maven		v3.6.3
gradle		v5.6.4
ECMAscript	v1.7
npm			6.14.4
node		v12.17.0
angular		v10.0.0
```

## 后端命名规范

### 包命名

| 类型                          | 名称                                        |
| :---------------------------- | ------------------------------------------- |
| 项目包名                      | me.dongqinglin.projectName                  |
| http接口包名                  | me.dongqinglin.projectName.controller       |
| service服务包名               | me.dongqinglin.projectName.service          |
| serviceImpl服务实现类包名     | me.dongqinglin.projectName.serviceImplement |
| DAO数据访问对象类包名         | me.dongqinglin.projectName.DAO              |
| DAOImpl数据访问对象实现类包名 | me.dongqinglin.projectName.DAOImplement     |
| entity实体类包名              | me.dongqinglin.projectName.entity           |
| bean封装类包名                | me.dongqinglin.projectName.bean             |
| config配置类包名              | me.dongqinglin.projectName.config           |
| utils工具类包名               | me.dongqinglin.projectName.utils            |
| filter过滤器包名              | me.dongqinglin.projectName.filter           |
| contrant常量类包名            | me.dongqinglin.projectName.contrant         |
|                               |                                             |

### 方法命名

| 类型     | 命名         |
| -------- | ------------ |
| 查找     | find()       |
| 模糊查找 | findLike()   |
| 根据查找 | findBy()     |
| 保存     | save()       |
| 保存为   | saveAs()     |
| 生成     | generate()   |
| 生成为   | generateAs() |
| 根据过滤 | filterBy()   |
|          |              |

### 方法体命名

| 类型   | 命名   |
| ------ | ------ |
| 返回值 | result |
|        |        |
|        |        |
|        |        |

## 后端有价值信息

### gradle 阿里云镜像

```gradle
plugins {
	id 'org.springframework.boot' version '2.3.1.RELEASE'
	id 'io.spring.dependency-management' version '1.0.9.RELEASE'
	id 'java'
}

group = 'me.dongqinglin'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

repositories {
	mavenLocal()
	maven { url "http://maven.aliyun.com/nexus/content/groups/public" }
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-devtools'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
	// https://mvnrepository.com/artifact/mysql/mysql-connector-java
	compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.20'
	// https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
	compile group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
	// https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
	compile group: 'javax.xml.bind', name: 'jaxb-api', version: '2.4.0-b180830.0359'

}

test {
	useJUnitPlatform()
}

```

### hibernate 自定义sql语句突破table语法

```
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TableDaoImpl implements TableDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> showTables() {
        String showTableSql = "show tables;";
        Query showTableQuery = entityManager.createNativeQuery(showTableSql);
        return showTableQuery.getResultList();
    }

    @Override
    public List showColumnsByTableName(String tableName) {
        String showTableColumnsSql = "show columns from " + tableName + ";";
        Query showTableQuery = entityManager.createNativeQuery(showTableColumnsSql);
        List resultList = showTableQuery.getResultList();
        return resultList;
    }

    @Override
    public List selectDatasBy(String tableName) {
        String showTableColumnsSql = "select * from " + tableName + ";";
        Query showTableQuery = entityManager.createNativeQuery(showTableColumnsSql);
        List resultList = showTableQuery.getResultList();
        return resultList;
    }

    @Override
    @Transactional
    public void deleteFrom(String tableName) {
        try {
            String dropTableSql = "drop table " + tableName + ";";
            System.out.println(dropTableSql);
            Query deleteTableQuery = entityManager.createNativeQuery(dropTableSql);
            deleteTableQuery.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
```



## 后端情景

### 跨域

跨域是一个前后端分离的常见问题，Spring boot有跨域的基本解决方案。主要是配置解决。

```java
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfigurer implements WebMvcConfigurer {
    private ApplicationContext applicationContext;

    public WebConfigurer() {
        super();
    }

    /**
     * 静态资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
                //添加映射路径
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                //是否发送Cookie信息
                .allowCredentials(true)
                //放行哪些原始域(请求方式)
                .allowedMethods("GET","POST", "PUT", "DELETE")
                //放行哪些原始域(头部信息)
                .allowedHeaders("*")
                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                .exposedHeaders("Header1", "Header2");
    }
}
```



### 文件系统

文件系统时后端服务器常见集成部分，Spring boot有文件系统的基本解决方案。包括配置和接口共同解决。其中配置解决下载部分，接口解决上传部分。

1. 配置

   配置主要分为两个部分，一个是上传文件的路径和大小限制。一部分为路径的静态访问控制。

   ```properties
   # 文件服务器 自定义配置文件属性规范名称应为kebab-case（ - 分隔）
   #文件上传目录（注意Linux和Windows上的目录结构不同）
   #file.uploadFolder=/root/uploadFiles/
   file-service.profile = D:/profile/
   spring.servlet.multipart.max-file-size=10MB
   spring.servlet.multipart.max-request-size=1000MB
   ```

   ```java
   @EnableWebMvc
   public class MyWebConfigurer implements WebMvcConfigurer {
       private ApplicationContext applicationContext;
       @Value("${file-service.profile}")
       private String uploadFilePath;
   
       public MyWebConfigurer() {
           super();
       }
   
       /**
        * 静态资源配置
        * @param registry
        */
       @Override
       public void addResourceHandlers(ResourceHandlerRegistry registry) {
           registry.addResourceHandler("/**")
                   .addResourceLocations("classpath:/META-INF/resources/")
                   .addResourceLocations("classpath:/resources/")
                   .addResourceLocations("classpath:/static/")
                   .addResourceLocations("classpath:/public/")
                   .addResourceLocations("file:" + uploadFilePath);
           WebMvcConfigurer.super.addResourceHandlers(registry);
       }
   }
   ```

   

2. 接口

   文件服务器接口通常只保留一个上传文件接口，下载文件则通过静态资源访问。

   具体下载的文件如果是html或者图片等能直接被浏览器识别的，就会被浏览器拦截下来，需要用户主动的另存为，这一行为按照浏览器的规范做。当然也可以直接下载，不理会浏览器，以后再考虑。

   上传接口如下：

   ```java
   import net.minidev.json.JSONObject;
   import org.springframework.beans.factory.annotation.Value;
   import org.springframework.web.bind.annotation.*;
   import org.springframework.web.multipart.MultipartFile;
   
   import java.io.File;
   
   @RestController
   @RequestMapping("file")
   public class FileController {
       @Value("${file-service.profile}")
       private String uploadFilePath;
   
       @PostMapping("/upload")
       public JSONObject httpUpload(@RequestParam MultipartFile files[]){
           JSONObject object=new JSONObject();
           for(int i=0;i<files.length;i++){
               String fileName = files[i].getOriginalFilename();  // 文件名
               File dest = new File(uploadFilePath +'/'+ fileName);
               if (!dest.getParentFile().exists()) {
                   dest.getParentFile().mkdirs();
               }
               try {
                   files[i].transferTo(dest);
               } catch (Exception e) {
                   object.put("success",2);
                   object.put("result","程序错误，请重新上传");
                   return object;
               }
           }
           object.put("success",1);
           object.put("result","文件上传成功");
           return object;
       }
   }
   ```

   

### 数据库

数据库是后端服务器常见的合成部分，Spring boot支持挂载不同开发商不同版本的数据库。我在这里选用比较熟悉的MySQL数据库。MySQL数据库主要使用hibernate规范进行操作。

主要通过配置和接口解决。配置解决MySQL连接、时区和MySQL语句生成部分，接口需要细分为控制器、服务、数据访问和实体层，从左到右依赖。

1. 配置

   ```properties
   # 数据库服务器
   # CST China Standard time 中国标准时间，为东八区时间，是UTC+8
   spring.datasource.url = jdbc:mysql://localhost:3306/spring_web_and_jpa?serverTimezone=CST
   spring.datasource.username = root
   spring.datasource.password = rootroot
   spring.datasource.driverClassName = com.mysql.jdbc.Driver
   # 时间格式和时区设置
   spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
   spring.jackson.time-zone=CST
           
   # Specify the DBMS
   # 制定数据库类型
   spring.jpa.database = MYSQL
   # Show or not log for each sql query
   # 是否在控制台打印每一条sql语句
   spring.jpa.show-sql = true
   # Hibernate ddl auto (create, create-drop, update)
   # hibernate 数据定义语言自动生成，有上面括号中的三个可能值
   spring.jpa.hibernate.ddl-auto = update
   #切换数据库引擎为innodb
   #spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect
   # Naming strategy
   ## 命名策略
   spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
   # stripped before adding them to the entity manager
   # 在将它们添加到实体管理器之前不进行操作
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
   ```

2. 接口

   控制器层

   ```java
   import java.util.List;
   import java.util.Optional;
   
   import me.dongqinglin.springWebAndJPA.model.User;
   import me.dongqinglin.springWebAndJPA.service.UserService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.*;
   
   @RestController
   @RequestMapping("/user")
   public class UserController {
       @Autowired
       private UserService userService;
       @PostMapping
       public void addUser(@RequestBody User user) { userService.save(user); }
       @PutMapping
       public void updateUser(@RequestBody User user) {userService.update(user);}
       @DeleteMapping("{id}")
       public void deleteUserBy(@PathVariable int id){userService.deleteById(id);}
       @GetMapping
       public List<User> getAllUser() { return userService.findAll(); }
       @GetMapping("{id}")
       public Optional<User> getUserBy(@PathVariable int id) { return userService.findById(id); }
       //@PostMapping("/getLikeBy")
       //public List<User> getUserLikeBy(@RequestBody User user) { return userService.findLikeBy(user); }
   }
   ```

   服务层包括接口和实现类。

   ```java
   import me.dongqinglin.springWebAndJPA.model.User;
   import java.util.List;
   import java.util.Optional;
   
   public interface UserService {
       public void save(User user);
       public void deleteById(int id);
       public List<User> findAll();
       public void update(User user);
       public Optional<User> findById(int id);
       //List<User> getLikeBy(User user);
   }
   
   ```

   ```java
   import java.util.List;
   import java.util.Optional;
   
   import me.dongqinglin.springWebAndJPA.DAO.UserDao;
   import me.dongqinglin.springWebAndJPA.model.User;
   import me.dongqinglin.springWebAndJPA.service.UserService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.stereotype.Service;
   
   @Service
   public class UserServiceImpl implements UserService {
       @Autowired
       private UserDao userDao;
       public void save(User user){userDao.save(user);}
       public void update(User user){userDao.save(user);}
       public void deleteById(int id){userDao.deleteById(id);}
       public List<User> findAll(){return userDao.findAll();}
       public Optional<User> findById(int id){return userDao.findById(id);}
       //List<User> findLikeBy(User user){return userDao.findLikeBy列名大驼峰(user.getUserby列名大驼峰());}
   }
   ```

   数据访问层，根据JPA规范，数据访问层一般只用接口，通过继承接口实现主要方法。

   ```java
   import me.dongqinglin.springWebAndJPA.model.User;
   import org.springframework.data.jpa.repository.JpaRepository;
   import org.springframework.stereotype.Repository;
   
   @Repository
   public interface UserDao extends JpaRepository<User,Integer> {
       //@Query(value = "select * from user as t where t.列名 like %?1%",nativeQuery = true)
       //List<UsersModel> findLikeBy列名大驼峰(String 列名小驼峰);
   }
   ```

   实体类层

   ```java
   import javax.persistence.*;
   import java.math.*;
   import java.sql.*;
   
   @Entity(name = "user")
   @Table(name = "user")
   public class User {
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private int id;
       @Column(name = "address", nullable = true, insertable = true, updatable = true, columnDefinition = "varchar(255)")
       private String address;
       @Column(name = "birth_day", nullable = true, insertable = true, updatable = true, columnDefinition = "datetime")
       private Timestamp birthDay;
       @Column(name = "sex", nullable = true, insertable = true, updatable = true, columnDefinition = "varchar(255)")
       private String sex;
       @Column(name = "user_name", nullable = true, insertable = true, updatable = true, columnDefinition = "varchar(255)")
       private String userName;
   
       public void setId(int id) {
           this.id = id;
       }
   
       public int getId() {
           return this.id;
       }
   
       public void setAddress(String address) {
           this.address = address;
       }
   
       public String getAddress() {
           return this.address;
       }
   
       public void setBirthDay(Timestamp birthDay) {
           this.birthDay = birthDay;
       }
   
       public Timestamp getBirthDay() {
           return this.birthDay;
       }
   
       public void setSex(String sex) {
           this.sex = sex;
       }
   
       public String getSex() {
           return this.sex;
       }
   
       public void setUserName(String userName) {
           this.userName = userName;
       }
   
       public String getUserName() {
           return this.userName;
       }
   }
   ```

### 用户口令

登陆系统是后端服务器的常见部分，spring boot有spring security的解决方案，但是设置过于复杂。具体需要配置security，重写UserDetailsService关联上JPA接口，并且设置tokenUtils和tokenFilter。

1. 配置

```java
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() { 
    	return NoOpPasswordEncoder.getInstance();
	}
}
```

2. 用户服务、实体和接口

```java
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException(("Not Found" + username)));
        return user.map(MyUserDetail::new).get();
    }
}
```

```java
public class MyUserDetail implements UserDetails {

    private String username;
    private String password;
    private boolean enable;
    private List<GrantedAuthority> authorityList;
    public MyUserDetail(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enable = user.getEnable();
        this.authorityList = Arrays.stream(user.getRoles().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}

```

```java
@Entity(name="user")
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    // GenerationType有四种可能值分别是AUTO,INDENTITY,SEQUENCE 和 TABLE。
    private int id;
    @Column(name="username", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String username;
    @Column(name="password", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String password;
    @Column(name="roles", nullable=true, insertable=true, updatable=true, columnDefinition="varchar(255)")
    private String roles;
    @Column(name="enable", nullable=false, insertable=true, updatable=true, columnDefinition="bit")
    private boolean enable;
    public void setId(int id){this.id = id;}
    public int getId(){return this.id;}
    public void setUsername(String username){this.username = username;}
    public String getUsername(){return this.username;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return this.password;}
    public void setRoles(String roles){this.roles = roles;}
    public String getRoles(){return this.roles;}
    public void setEnable(boolean enable){this.enable = enable;}
    public boolean getEnable(){return this.enable;}
}
```

```java
@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    //@Query(value = "select * from user as t where t.列名 like %?1%",nativeQuery = true)
    //List<UsersModel> findLikeBy列名大驼峰(String 列名小驼峰);

    Optional<User> findByUsername(String username);
}
```

3. tokenUtils和filter

```java
@Component
public class JwtUtil  {
    private String  SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokeExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 *60*60*10))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokeExpired(token));
    }
}
```

```java
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    MyUserDetailService userDetailService;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if(authenticationHeader !=null && authenticationHeader.startsWith("Bearer ")){
            jwt = authenticationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
```

4. 主验证接口

```java
@RestController
public class MainResoure {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private JwtUtil jwtTokenUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<?>  createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        System.out.println(authenticationRequest.getUsername());
        System.out.println(authenticationRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("incoorect username or PassWord", e);
        }
        final UserDetails userDetails = userDetailService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
```

5. 前端服务

```javascript
import { Injectable } from '@angular/core';
import { domain, jsonHttpOptions, authoriztion, authenticateOptions} from "../data/constrant";
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap } from 'rxjs/internal/operators/tap';
import { catchError } from 'rxjs/internal/operators/catchError';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  loginUrl: string = domain + "/authenticate";
  constructor(
    private http: HttpClient
  ) { }

  login(data) {
    
    // const params = typeof(data)==='object' && String(data) !== '[object File]'? this.paramFormat(data): data;
    // console.log(params)
    return this.http.post(this.loginUrl, data, jsonHttpOptions).pipe(
      tap(userToken => {
        this.log(`${JSON.stringify(userToken)}`);
        authoriztion.Authorization += userToken["jwt"]
        jsonHttpOptions.headers = new HttpHeaders(authoriztion);
        this.log(`${JSON.stringify(authoriztion)}`);
      }),
     catchError(this.handleError('addUser()'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {  
      console.error(error); 
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
  log(msg){ console.log(msg); }

  // 序列化参数
  private paramFormat (data: any): string {
    let paramStr='', name, value, subName, innerObj;
    for (name in data) {
        value = data[name];
        if (value instanceof Array) {
            for (let i of value) {
                subName = name;
                innerObj = {};
                innerObj[subName] = i;
                paramStr += this.paramFormat(innerObj) + '&';
            }
        } else if (value instanceof Object) {
            Object.keys(value).forEach(function(key){
                subName = name + '[' + key + ']';
                innerObj = {};
                innerObj[subName] = value[key];
                paramStr += this.paramFormat(innerObj) + '&';
            })
        } else if (value !== undefined && value !== null) {
            paramStr += encodeURIComponent(name) + '='
                + encodeURIComponent(value) + '&';
        }
    }
    return paramStr.length ? paramStr.substr(0, paramStr.length - 1) : paramStr;
  };
}

```

package me.dongqinglin;

import org.junit.Test;

// @RunWith(SpringRunner.class)
// @SpringBootApplication(scanBasePackageClasses = RedisConfig.class)
// @SpringBootTest
public class RedisDemoApplicationTests {

    // @Autowired
    // private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStringTemplate()
    {
        // stringRedisTemplate.opsForValue().set("Name", "Susan King");
        // stringRedisTemplate.opsForValue().set("Age", "29");
        // Assert.assertEquals("Susan King", stringRedisTemplate.opsForValue().get("Name"));
        // Assert.assertEquals("29", stringRedisTemplate.opsForValue().get("Age"));
    }
}
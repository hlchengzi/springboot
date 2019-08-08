package springbootredis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springbootredis.demo.util.RedisUtil;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    /**
    * 对redis常规操作测试
    */
    @Test
    public void redis() {
        Date dataStart = new Date();
        for (int i=0;i<10000;i++) {
            String key = String.valueOf(i);
            Object object = String.valueOf(1);
            redisUtil.set(key, object);
        }
        Date dateEnd = new Date();
        System.out.println(dataStart+"::"+dateEnd);
    }


}

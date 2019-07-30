package springbootredis.demo.Controller;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootredis.demo.entity.User;
import springbootredis.demo.service.IUserService;
import springbootredis.demo.util.RedisUtil;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xcxu
 * @since 2019-07-30
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    RedisUtil redisUtil;

    @Autowired
    IUserService iUserService;

    private static final int capacity = 1000000;
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);
    static{
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
    }


    /**
    * @Description: 根据id获取用户信息
    * @Param:  id
    * http://localhost:8081/user/selectById
    * @Author: xuxiangcheng
    * @Date: 2019/7/30
    */
    @GetMapping("/selectById")
    @ResponseBody
    public User selectById(@RequestParam Integer id){
        //log.info("Execute UserController selectById method");

        try {
            if(bloomFilter.mightContain(id)){
                Object object = redisUtil.get(String.valueOf(id));
                if(ObjectUtils.isEmpty(object)){
                    User user = iUserService.getById(id);
                    if(!ObjectUtils.isEmpty(user)){
                        //对于热点key，设置永不过期，防止雪崩
                        redisUtil.set(String.valueOf(id),user);
                        log.info("Get User from MySQL   "+id);
                        return user;
                    }else {
                        //防止穿透
                        redisUtil.set(String.valueOf(id),"",3000);
                        return null;
                    }
                }else {
                    log.info("Get User from redis   "+id);
                    return (User)object;
                }
            }else {
                log.info("Get User exist from   bloomFilter  "+id);
                return null;
            }

        }catch (Exception e) {
            e.printStackTrace();
            log.error("UserController selectById： {}", e.getMessage());
            return null;
        }
    }
}

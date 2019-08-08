package springbootredis.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springbootredis.demo.entity.User;
import springbootredis.demo.mapper.UserMapper;
import springbootredis.demo.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author HF.L
 * @since 2019-07-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

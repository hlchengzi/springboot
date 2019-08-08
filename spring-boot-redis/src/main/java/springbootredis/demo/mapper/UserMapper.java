package springbootredis.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import springbootredis.demo.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author HF.L
 * @since 2019-07-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

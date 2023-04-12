package org.rxy.mapper;

import org.rxy.entity.EfmUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZMN
 * @since 2023-04-12
 */
@Repository
public interface EfmUserMapper extends BaseMapper<EfmUser> {
    List<EfmUser> findEfmUserPageList(Page<EfmUser> page,String fuzzySearchStr);
}
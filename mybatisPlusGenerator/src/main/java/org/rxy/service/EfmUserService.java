package org.rxy.service;

import org.rxy.entity.EfmUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZMN
 * @since 2023-04-12
 */
public interface EfmUserService extends IService<EfmUser> {

        /**
         *  分页查询
         * @param pageQuery 必须要有 page 和 row
         * @return
         */
        Page<EfmUser> selectEfmUserPage(PageQuery pageQuery);

        boolean IsNameExists(String name, Integer id);
}
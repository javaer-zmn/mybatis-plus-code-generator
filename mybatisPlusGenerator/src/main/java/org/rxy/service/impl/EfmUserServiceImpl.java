package org.rxy.service.impl;

import org.rxy.entity.EfmUser;
import org.rxy.mapper.EfmUserMapper;
import org.rxy.service.EfmUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.SimpleDateFormat;
import java.util.List;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZMN
 * @since 2023-04-12
 */
@Service
@Transactional
public class EfmUserServiceImpl extends ServiceImpl<EfmUserMapper, EfmUser> implements EfmUserService {
        @Autowired
        private EfmUserMapper efmUserMapper;

        @Override
        public Page<EfmUser> selectEfmUserPage(PageQuery pageQuery) {
                String fuzzySearchStr = ""; // 模糊查询字段
                Page<EfmUser> page = new Page<>(pageQuery.getPage(),pageQuery.getRow());
                List<EfmUser> list = efmUserMapper.findEfmUserPageList(page,fuzzySearchStr);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                list.forEach(item->{
                item.setAddTimeStr(simpleDateFormat.format(item.getAddTime()));
                });
                page.setRecords(list);
                return page;
        }

        /**
        * 查询名称是否存在
        * @param name 名称
        * @param id 主键
        * @return
        */
        @Override
        public boolean IsNameExists(String name, Integer id) {
                QueryWrapper<EfmUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().and(c->c.eq(EfmUser::getName,name));
                if (0 != id) {
                queryWrapper.lambda().and(c->c.ne(EfmUser::getId, id));
                }
                return efmUserMapper.selectCount(queryWrapper)>0;
        }
}

package org.rxy.controller;

import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.servlet.http.HttpServletRequest;
import lombok.var;
import java.util.List;
import org.rxy.service.EfmUserService;
import org.rxy.entity.EfmUser;
import java.text.SimpleDateFormat;

/**
 *
 * @author ZMN
 * @since 2023-04-12
 */
@RestController
@RequestMapping("EfmUser/")
@Api(value = "/EfmUserController", tags = "")
public class EfmUserController {
    @Autowired
    public EfmUserService iEfmUserService;

    @Autowired
    private HttpServletRequest request;

    // 用户信息对象，响应对象BoardMessage的最后一个参数，测试时不需要验证用户信息暂时给null值，放开此项同时相应的应该把每个接口中【验证用户信息的代码】删除，项目中可把此参数删除
    // private ModelAccountInfo tempVal = null;

    /**
     * 分页查询数据
     *
     * @param pageQuery  查询条件 Page,Row 为必要参数
     * @return BoardMessage
     */
    @PostMapping(value = "GetEfmUserPageList")
    @ResponseBody
    @ApiOperation(value = "获取分页列表", notes = "获取分页列表", response = BoardMessage.class)
    public BoardMessage GetEfmUserPageList(@RequestBody PageQuery pageQuery) {

        // 验证用户信息
        var tempVal = TokenUse.verificationTokenInfo(request);
        if (tempVal == null) {
            return new BoardMessage(ErrorCode.getTokenId(), "Token无效，请重新登入", " GetEfmUserPageList",null);
        }

        BoardMessage message;
        try {
            Page<EfmUser> page = iEfmUserService.selectEfmUserPage(pageQuery);
            return new BoardMessage(ErrorCode.getSuccess(),"",page," GetEfmUserPageList", tempVal);
        } catch (Exception e) {
            message = new BoardMessage(ErrorCode.getException(),e.toString()," GetEfmUserPageList", tempVal);
        }
        return message;
    }

    /**
    * 查询列表数据
    * @return BoardMessage
    */
    @GetMapping(value = "GetEfmUserList")
    @ResponseBody
    @ApiOperation(value = "查询列表数据", notes = "查询列表数据", response = BoardMessage.class)
    public BoardMessage GetEfmUserList() {

        // 验证用户信息
        var tempVal = TokenUse.verificationTokenInfo(request);
        if (tempVal == null) {
            return new BoardMessage(ErrorCode.getTokenId(), "Token无效，请重新登入", " GetEfmUserList",null);
        }

        BoardMessage message;
        try {
            List<EfmUser> list = iEfmUserService.list();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            list.forEach(item->{
            item.setAddTimeStr(simpleDateFormat.format(item.getAddTime()));
            });
            return new BoardMessage(ErrorCode.getSuccess(),"",list," GetEfmUserList", tempVal);
        } catch (Exception e) {
            message = new BoardMessage(ErrorCode.getException(),e.toString()," GetEfmUserList", tempVal);
        }
        return message;
    }

    /**
     * 查询树列表
     * @return BoardMessage
     */
    @PostMapping(value = "GetEfmUserTreeList")
    @ResponseBody
    @ApiOperation(value = "查询树列表", notes = "查询树列表", response = BoardMessage.class)
    public BoardMessage GetEfmUserTreeList() {

        // 验证用户信息
        var tempVal = TokenUse.verificationTokenInfo(request);
        if (tempVal == null) {
        return new BoardMessage(ErrorCode.getTokenId(), "Token无效，请重新登入", " GetEfmUserTreeList",null);
        }

        BoardMessage message;
        try {
        List<EfmUser> list = iEfmUserService.list();
        Map<Integer, List<EfmUser>> mapByPid = list.stream().collect(Collectors.groupingBy(EfmUser::getPId));
        list.forEach(department->{
        department.setChildren(mapByPid.get(department.getId()));
        });
        List<EfmUser> collect = list.stream().filter(v -> v.getPId().equals(0)).collect(Collectors.toList());
        return new BoardMessage(ErrorCode.getSuccess(),"",collect," GetEfmUserTreeList", tempVal);
        } catch (Exception e) {
        message = new BoardMessage(ErrorCode.getException(),e.toString()," GetEfmUserTreeList", tempVal);
        }
        return message;
    }

    /**
     * 添加EfmUser
     * @param vueEfmUser 请求参数对象
     * @return BoardMessage
     */
    @PostMapping(value = "InsertEfmUser")
    @ApiOperation(value = "添加EfmUser", notes = "添加EfmUser", response = BoardMessage.class)
    @ResponseBody
    public BoardMessage InsertEfmUser(@RequestBody VueEfmUser vueEfmUser) {

        // 验证用户信息
        var tempVal = TokenUse.verificationTokenInfo(request);
        if (tempVal == null) {
            return new BoardMessage(ErrorCode.getTokenId(), "Token无效，请重新登入", tempVal);
        }

        BoardMessage message;
        try {
            if (iEfmUserService.IsNameExists(vueEfmUser.getName(), 0)) {
                message = new BoardMessage(ErrorCode.getFail(), "名称已存在", "InsertEfmUser", tempVal);
                return message;
            }
            EfmUser demoEfmUser = new EfmUser();
            // 此处为对象赋值(把第一个参数对象的属性值赋给第二个参数对象)
            BeanUtils.copyProperties(vueEfmUser,demoEfmUser);

            if (iEfmUserService.save(demoEfmUser)) {
                message = new BoardMessage(ErrorCode.getSuccess(), "", "InsertEfmUser", tempVal);
            } else {
                message = new BoardMessage(ErrorCode.getFail(), "", "InsertEfmUser", tempVal);
            }
        } catch (Exception e) {
            message = new BoardMessage(ErrorCode.getException(), e.toString(), "InsertEfmUser", tempVal);
        }
        return message;
    }

    /**
     * 修改EfmUser
     * @param vueEfmUser 请求参数对象
     * @return BoardMessage
     */
    @PostMapping(value = "UpdateEfmUser")
    @ApiOperation(value = "修改EfmUser", notes = "修改EfmUser", response = BoardMessage.class)
    @ResponseBody
    public BoardMessage UpdateEfmUser(@RequestBody VueEfmUser vueEfmUser) {

        // 验证用户信息
        var tempVal = TokenUse.verificationTokenInfo(request);
        if (tempVal == null) {
            return new BoardMessage(ErrorCode.getTokenId(), "Token无效，请重新登入", tempVal);
        }

        BoardMessage message;
        try {
            if (iEfmUserService.IsNameExists(vueEfmUser.getName(),vueEfmUser.getId())) {
                message = new BoardMessage(ErrorCode.getFail(), "名称已存在", "UpdateEfmUser", tempVal);
                return message;
            }
            EfmUser demoEfmUser = new EfmUser();
            // 此处为对象赋值(把第一个参数对象的属性值赋给第二个参数对象)
            BeanUtils.copyProperties(vueEfmUser,demoEfmUser);

            if (iEfmUserService.updateById(demoEfmUser)) {
                message = new BoardMessage(ErrorCode.getSuccess(), "", "UpdateEfmUser", tempVal);
            } else {
             message = new BoardMessage(ErrorCode.getFail(), "", "UpdateEfmUser", tempVal);
            }
        } catch (Exception e) {
            message = new BoardMessage(ErrorCode.getException(), e.toString(), "UpdateEfmUser", tempVal);
        }
        return message;
    }

    /**
     * 删除EfmUser
     * @param id EfmUser主键
     * @return BoardMessage
     */
    @PostMapping(value = "DeleteEfmUser")
    @ApiOperation(value = "删除EfmUser", notes = "删除EfmUser", response = BoardMessage.class)
    @ResponseBody
    public BoardMessage DeleteEfmUser(@RequestParam("id") int id){

        // 验证用户信息
        var tempVal=TokenUse.verificationTokenInfo(request);
        if(tempVal==null){
            return new BoardMessage(ErrorCode.getTokenId(),"Token无效，请重新登入",tempVal);
        }

        BoardMessage message;
        try{
            if(iEfmUserService.removeById(id)){
                return new BoardMessage(ErrorCode.getSuccess(),"DeleteEfmUser",tempVal);
            }else{
                return new BoardMessage(ErrorCode.getFail(),"DeleteEfmUser",tempVal);
            }
        }catch(Exception e){
            message=new BoardMessage(ErrorCode.getException(),e.toString(),"DeleteEfmUser",tempVal);
        }
        return message;
    }

    /**
     * 启用/禁用EfmUser
     * @param vueEfmUser 请求参数对象 IsEnable,Id 为必要参数
     * @return  BoardMessage
     */
    @PostMapping(value = "EnableEfmUser")
    @ApiOperation(value = " 启用/禁用EfmUser", notes = "启用/禁用EfmUser", response = BoardMessage.class)
    @ResponseBody
    public BoardMessage EnableEfmUser(@RequestBody VueEfmUser vueEfmUser) {

        // 验证用户信息
        var tempVal = TokenUse.verificationTokenInfo(request);
        if (tempVal == null) {
            return new BoardMessage(ErrorCode.getTokenId(), "Token无效，请重新登入", tempVal);
        }

        BoardMessage message;
        try {
            EfmUser demoEfmUser = new EfmUser();
            demoEfmUser.setIsEnable(vueEfmUser.getIsEnable());
            demoEfmUser.setId(vueEfmUser.getId());

            if (iEfmUserService.updateById(demoEfmUser)) {
                message = new BoardMessage(ErrorCode.getSuccess(), "", "EnableEfmUser", tempVal);
            } else {
                message = new BoardMessage(ErrorCode.getFail(), "", "EnableEfmUser", tempVal);
            }
        } catch (Exception e) {
            message = new BoardMessage(ErrorCode.getException(), e.toString(), "EnableEfmUser", tempVal);
        }
        return message;
    }

}

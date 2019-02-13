package com.yfancy.service.user.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yfancy.common.base.entity.User;
import com.yfancy.service.user.api.service.UserDubboService;
import com.yfancy.service.user.api.vo.UserVo;
import com.yfancy.service.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service(version = "1.0.0", interfaceClass = UserDubboService.class, timeout = 3000)
@Transactional(rollbackFor = Exception.class)
public class UserDubboServiceImpl implements UserDubboService {


    @Autowired
    private UserDao userDao;

    @Override
    public UserVo getUserInfoById(int id) {
        log.info("【dubbo】【server】,开始查询用户信息，开始，id = {}", id);
        UserVo userVo = new UserVo();
        User userById = userDao.getUserById(id);
        BeanUtils.copyProperties(userById, userVo);
        log.info("【dubbo】【server】,开始查询用户信息，userVo = {}", userVo);
        return userVo;
    }

    @Override
    public List<UserVo> getAllUser() {
        log.info("【dubbo】【server】,开始查询全部用户信息，开始");
        List<UserVo> list = new ArrayList<>();
        List<User> allUser = userDao.getAllUser();
        for(User user : allUser){
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            list.add(userVo);
        }
        log.info("【dubbo】【server】,开始查询全部用户信息，结束，list = {}" ,list);
        return list;
    }

    @Override
    @Transactional
    public void addUser(UserVo userVo){
        log.info("【dubbo】【server】,新增用户，开始，userVo = {}" , userVo);
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        userDao.insert(user);
    }

    @Override
    public UserVo getUserInfoByName(String name) {
        log.info("【dubbo】【server】,获取用户，开始，name = {}" , name);
        User user = userDao.getUserByName(name);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        log.info("【dubbo】【server】,获取用户，结束，userVo = {}" , userVo);
        return userVo;
    }


}

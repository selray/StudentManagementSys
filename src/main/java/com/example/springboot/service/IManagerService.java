package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Manager;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sel
 * @since 2023-03-31
 */
public interface IManagerService extends IService<Manager> {

    UserDTO login(UserDTO userDTO);
}

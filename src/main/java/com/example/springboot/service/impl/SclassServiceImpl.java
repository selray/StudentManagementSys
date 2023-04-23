package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Sclass;
import com.example.springboot.mapper.SclassMapper;
import com.example.springboot.service.ISclassService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sel
 * @since 2023-04-05
 */
@Service
public class SclassServiceImpl extends ServiceImpl<SclassMapper, Sclass> implements ISclassService {

}

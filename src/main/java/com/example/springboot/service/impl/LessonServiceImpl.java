package com.example.springboot.service.impl;

import com.example.springboot.entity.Lesson;
import com.example.springboot.mapper.LessonMapper;
import com.example.springboot.service.ILessonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sel
 * @since 2023-04-04
 */
@Service
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements ILessonService {

}

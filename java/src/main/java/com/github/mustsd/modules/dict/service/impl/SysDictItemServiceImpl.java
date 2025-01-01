package com.github.mustsd.modules.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mustsd.modules.dict.entity.SysDictItem;
import com.github.mustsd.modules.dict.mapper.SysDictItemMapper;
import com.github.mustsd.modules.dict.service.ISysDictItemService;
import org.springframework.stereotype.Service;

@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem>
    implements ISysDictItemService {}

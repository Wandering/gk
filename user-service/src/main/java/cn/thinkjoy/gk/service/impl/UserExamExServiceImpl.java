/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: shishuo
 * $Id:  UserExamServiceImpl.java 2015-10-23 14:44:11 $
 */
package cn.thinkjoy.gk.service.impl;

import cn.thinkjoy.gk.dao.IUserExamDAO;
import cn.thinkjoy.gk.domain.UserExam;
import cn.thinkjoy.gk.service.IUserExamExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UserExamServiceImpl")
public class UserExamExServiceImpl implements IUserExamExService{

    @Autowired
    private IUserExamDAO userExamDAO;

}

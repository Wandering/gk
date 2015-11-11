/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: timer
 * $Id:  MajoredRankService.java 2015-11-10 15:57:32 $
 */

package cn.thinkjoy.gk.service;

import cn.thinkjoy.gk.pojo.MajoredRankDto;

import java.util.List;

public interface IMajoredRankExService{

    List<MajoredRankDto> findOpenUniversity(String majorName, Integer year,Long areaId,Integer pageSize,Integer pageNo);

}

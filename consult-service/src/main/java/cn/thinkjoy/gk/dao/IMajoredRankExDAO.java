/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: timer
 * $Id:  MajoredRankDAO.java 2015-11-10 15:57:32 $
 */
package cn.thinkjoy.gk.dao;


import cn.thinkjoy.gk.pojo.MajoredRankDto;

import java.util.List;
import java.util.Map;

public interface IMajoredRankExDAO{
	
    List<MajoredRankDto> findOpenUniversity(Map<String,Object> params);

}

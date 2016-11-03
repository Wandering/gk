package cn.thinkjoy.gk.service.information.service.ex;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

/**
 * Created by yangyongping on 2016/11/2.
 */
public interface IExpertOrderExService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {
}

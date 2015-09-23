package cn.thinkjoy.gk.protocol;

import java.util.List;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 14/12/27 下午10:03<br/>
 * 
 * @author qyang
 * @since v0.0.1
 */
public class ListWrapper<T> {
	private List<T> lists;

	public List<T> getLists() {
		return lists;
	}

	public void setLists(List<T> lists) {
		this.lists = lists;
	}
}

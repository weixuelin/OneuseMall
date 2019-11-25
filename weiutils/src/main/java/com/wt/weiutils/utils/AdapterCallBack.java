package com.wt.weiutils.utils;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;


/**
 * 局部刷新
 * @param <T>  泛型数据
 */
public abstract class AdapterCallBack<T> extends DiffUtil.Callback {

   public List<T> oldList;
   public List<T> newList;

    public AdapterCallBack(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;

    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }


}

package com.brethes.utils;

/**
 * Created by tof on 18/11/2015.
 * let easy One to One conversion between model and view
 */
public interface BidiBinderConverter<V,M> {
    public V convertFromModel(M dataFromModel) ;
    public M convertFromView(V dataFromView) ;
}

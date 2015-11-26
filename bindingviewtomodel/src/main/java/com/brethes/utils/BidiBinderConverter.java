
/*
 * Copyright (c) 2015. Christophe BRETHES.
 * You can copy, modify, use this for your application and if this stuff is usefull for you,
 *  just let me know 'cbrethes at gmail.com' http://devbreezecorner.blogspot.fr/
 */

package com.brethes.utils;

/**
 * let easy One to One conversion between model and view
 */
public interface BidiBinderConverter<V,M> {
    V convertFromModel(M dataFromModel) ;
    M convertFromView(V dataFromView) ;
}

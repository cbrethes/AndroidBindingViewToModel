package com.brethes.utils;

import android.view.View;

/**
 * Interface to be notified when view data is being applied to datamodel or to a special part.
 * for example, the afterUpdate could be used to persist the datamodel.
 *
 */
public interface BidiModelChangeWatcher  {
    //note that this property must be alphabetacally sorted after any propertyname. pray for no accentuation.
    public static final String ALL_PROPERTIES = "|ALL" ;

    /**
     * called juste before the setter on dataModel is about to be call.
     * return false to block the binding (dataModel stay unchanged and after update wont be called), true otherwise. Usually, the function may return true.
     * use
     * @param eventOrigin the view that trigger the change
     * @param dataModel the root datamodel
     * @param propertyChain the properties Path where the set is gona be done like "myPropertyName" for a direct property or  "myObjectProperty.aProperty" for a nested one. use ALL_PROPERTIES to listen on all properties
     * @param oldValue the current value of the property
     * @param newValue the value that is going to be set.
     * @return false to block the binding (dataModel stay unchanged and after update wont be called), true otherwise.
     */
    public boolean beforeUpdate(View eventOrigin, Object dataModel, String propertyChain, Object oldValue, Object newValue) ;
    public void afterUpdate(View eventOrigin, Object dataModel, String propertyChain, Object oldValue, Object newValue) ;
}

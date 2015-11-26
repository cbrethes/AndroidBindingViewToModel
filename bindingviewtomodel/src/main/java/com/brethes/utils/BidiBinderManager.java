package com.brethes.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;
import java.util.WeakHashMap;

import javadz.beanutils.PropertyUtils;

/**
 * Created by tof on 18/11/2015.
 */
public class BidiBinderManager {
    public enum EVENT_PROPERTYCHANGE {TEXT,CHECKED,CLICK}
    //datamodel -> listeners
    protected static WeakHashMap<Object,HashMap<String,HashSet<BidiModelChangeWatcher>>> modelWatchers = new WeakHashMap<Object,HashMap<String,HashSet<BidiModelChangeWatcher>>>() ;
    private static final String TAG = "BidiBinding" ;
    /*
     */
    protected static Object factoriseCodeGetValueToSet(BidiBinderConverter converter,Object value) {
        if(converter != null) {
            return  converter.convertFromView( value);
        }
        else {
            return  value ;
        }
    }
    protected static void factoriseCodeSetValue(View eventOrigin, WeakReference dataModelReference, String propertyChain, Object valueToSet, boolean propagateOnlyTrueChange  ) {

        if(dataModelReference.get() == null ) {
            Log.w(TAG, "cant bind to model, datamodel has been garbaged") ;
        }
        else {
            try {
                Object oldValue = null ;
                boolean doSetValue = false ;
                try {
                    oldValue =  PropertyUtils.getNestedProperty(dataModelReference.get(), propertyChain);
                    if(propagateOnlyTrueChange ) {
                        if( (oldValue == null && valueToSet != null) || (oldValue != null && valueToSet == null) ){
                            doSetValue  =true;
                        }
                        else if( oldValue == null && valueToSet == null) {
                            doSetValue = false ;
                        }
                        else {
                            doSetValue =  ! oldValue.equals(valueToSet) ;
                        }
                    }
                    else {
                        //always propagate change, even if they re the same
                        doSetValue = true ;
                    }
                } catch (NoSuchMethodException e) {
                    Log.w(TAG,"error while getting old property " + propertyChain + "on " + dataModelReference.get().getClass().getSimpleName(), e);
                }

                if(doSetValue) {
                    boolean applySetter = BidiBinderManager.dispatchToBidiModelChangeWatcher(dataModelReference.get(),eventOrigin,propertyChain,oldValue,valueToSet,true );
                    if(applySetter) {
                        try {
                            PropertyUtils.setNestedProperty(dataModelReference.get(), propertyChain, valueToSet);
                            Log.d(TAG, dataModelReference.get().getClass().getName() + "." + propertyChain + " setted to " + valueToSet);
                            BidiBinderManager.dispatchToBidiModelChangeWatcher(dataModelReference.get(), eventOrigin, propertyChain, oldValue, valueToSet, false);
                        } catch (NoSuchMethodException e) {
                            Log.e(TAG, dataModelReference.get().getClass().getName() + "." + propertyChain + " setter failed with value " + valueToSet, e);
                        }
                    }
                    else {
                        Log.d(TAG,dataModelReference.get().getClass().getName()+"." + propertyChain + " setter cancelled by BidiChangeListener") ;
                    }
                }
                else {
                    Log.d(TAG,dataModelReference.get().getClass().getName()+"." + propertyChain + " binding ignored") ;
                }
            } catch (IllegalAccessException e) {
                Log.w(TAG,"error while setting field on model", e) ;
            } catch (InvocationTargetException e) {
                Log.w(TAG, "error while setting field on model", e) ;
            }
        }
    }

    public static class GenericWatcher implements TextWatcher, CompoundButton.OnCheckedChangeListener , RadioGroup.OnCheckedChangeListener {


        //log purpose
        protected final String objectTypeName;

        protected final String propertyChain;
        private final boolean propagateOnlyTrueChange;
        private final WeakReference dataModelReference;
        //We need to keep a reference to pass it to BidiModelChangeWatcher and because  TextEventListener doesnt provide it
        private final WeakReference<View> eventOriginReference;
        //if we got a converter
        BidiBinderConverter<String, ?> converter ;

        protected GenericWatcher(WeakReference dataModelReference,View eventOrigin ,
                              boolean propagateOnlyTrueChange, String propertyChain, BidiBinderConverter<String, ?> converter) {
            this.converter = converter;
            this.eventOriginReference = new WeakReference<View>(eventOrigin) ;
            this.dataModelReference = dataModelReference;
            this.objectTypeName = dataModelReference.getClass().getName();
            this.propertyChain = propertyChain;
            this.propagateOnlyTrueChange = propagateOnlyTrueChange ;
        }

        @Override
        public void afterTextChanged(Editable s) {
            //do nothing
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
            //do nothing

        }
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            Object value  = factoriseCodeGetValueToSet(converter, s.toString()) ;
            factoriseCodeSetValue(eventOriginReference.get() ,dataModelReference, propertyChain,value,propagateOnlyTrueChange) ;
            return ;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Object value  = factoriseCodeGetValueToSet(converter, isChecked) ;
            factoriseCodeSetValue(buttonView,dataModelReference, propertyChain,value,propagateOnlyTrueChange) ;

            return ;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Object value  = factoriseCodeGetValueToSet(converter, checkedId) ;
            factoriseCodeSetValue(group,dataModelReference, propertyChain,value,propagateOnlyTrueChange) ;
            return ;
        }

    }

    public static void bind(View view, EVENT_PROPERTYCHANGE event, Object dataModel,String propertyChain ,
                           BidiBinderConverter optionalConverter,  boolean onlyTrueChange , boolean viewToModel, boolean modelToView ) {
        //check unsupportedFeature
        if(modelToView) {
            throw new UnsupportedOperationException("Not coded Yet, use databinding instead for now") ;
        }
        //doing binding hard coded... could be done with introspection, but we can see clearly what we support
        if(event == EVENT_PROPERTYCHANGE.TEXT) {
            TextView casted = (TextView) view ;
            casted.addTextChangedListener(new GenericWatcher( new WeakReference(dataModel),view, onlyTrueChange, propertyChain, optionalConverter));
            Log.d(TAG, "[BIND "+ event.name() + " CREATED] "+view.getResources().getResourceEntryName(casted.getId()) + "->" + dataModel.getClass().getSimpleName()+"."+propertyChain);
        }
        else if(event == EVENT_PROPERTYCHANGE.CHECKED) {
            if(view instanceof CompoundButton) {
                CompoundButton casted = (CompoundButton) view ;
                //dont break any existing listener
                ChainedListener.addOnCheckedChangeListener(casted,new GenericWatcher(new WeakReference(dataModel),view,onlyTrueChange,propertyChain,optionalConverter));
                Log.d(TAG, "[BIND "+ event.name() + " CREATED] " + view.getResources().getResourceEntryName(casted.getId()) + "->" + dataModel.getClass().getSimpleName() + "." + propertyChain);
            }
            else if(view instanceof RadioGroup) {
                RadioGroup casted = (RadioGroup) view ;
                //dont break any existing listener
                ChainedListener.addOnCheckedChangeListener(casted, new GenericWatcher(new WeakReference(dataModel),view, onlyTrueChange, propertyChain, optionalConverter));
                Log.d(TAG, "[BIND "+ event.name() + " CREATED] " + view.getResources().getResourceEntryName(casted.getId()) + "->" + dataModel.getClass().getSimpleName() + "." + propertyChain);
            }
            else {
                throw new UnsupportedOperationException("unbindable view for check event") ;
            }
        }
        else if(event == EVENT_PROPERTYCHANGE.CLICK) {
            throw new UnsupportedOperationException("not coded yet") ;
        }

    }

    /**
     * the simpliest way to bind view change to model.for now, it supports :
     * <ul><li>TextView (EditView,AutoComplete....) and listen text change. the value type is String </li>
     * <li> CheckBox, RadioButton, Switch, SwitchCompat, ToggleButton  and listen on checked/unchecked. the value type is boolean</li>
     * <li>RadioGroup and listen selectedItem change. the value type is the id of the selected view.</li></ul>
     * @param view
     * @param dataModel the instance where to store data
     * @param propertyChain the property (like in commons BeanUtils )that could be simple or refer to nested property eg "aProperty.subProperty" .
     */
    public static void bind(View view, Object dataModel,String propertyChain ) {
        if(view instanceof CompoundButton ) {
            bind(view, EVENT_PROPERTYCHANGE.CHECKED, dataModel, propertyChain, null, true, true, false) ;
        }
        else if(view instanceof TextView) {
            bind(view, EVENT_PROPERTYCHANGE.TEXT, dataModel, propertyChain,null , true, true, false) ;
        }
        else {
            throw new UnsupportedOperationException("Generic Binding is not supported for this kind of view. please try an other binding method");
        }

    }

    /**
     * add a watcher on the entire datamodel or on a specific part.
     * note that the notification events  bubble up through property depth hierarchy eg if the nested property "a.b.c" is modified, any watcher on "a.b.c, a.b ,a or and ALL_PROPERTIES" will be notified
     * the order of notification is not garanteed for 2 watcher listening the same property.
     * the order of notification is from the more specific to the less specific eg a watcher that watch for property "a.b.c" will called before a watcher on "a.b.c" if property c is modified
     * @param dataModel
     * @param propertyChain
     * @param modelWatcher
     */
    public static void registerDataModelChangeWatcher(@NonNull Object dataModel,@NonNull String propertyChain,@NonNull BidiModelChangeWatcher modelWatcher) {
        HashMap<String, HashSet<BidiModelChangeWatcher>> watcherOnOneInstance = modelWatchers.get(dataModel);
        if(watcherOnOneInstance == null) {
            watcherOnOneInstance = new HashMap<String, HashSet<BidiModelChangeWatcher>>() ;
            modelWatchers.put(dataModel,watcherOnOneInstance) ;
        }
        HashSet<BidiModelChangeWatcher> watcherOnOneProperty = watcherOnOneInstance.get(propertyChain);
        if(watcherOnOneProperty == null) {
            watcherOnOneProperty = new HashSet<BidiModelChangeWatcher>() ;
            watcherOnOneInstance.put(propertyChain,watcherOnOneProperty) ;
        }
        watcherOnOneProperty.add(modelWatcher) ;
        Log.d(TAG, "BidiModelChangeListener registered on " + dataModel.getClass().getSimpleName() + "." + propertyChain) ;

    }

    /**
     * Note : for now we notify all watcher even if beforeCallBack return false...
     * @param dataModel
     * @param eventOrigin
     * @param propertyChain
     * @param oldValue
     * @param newValue
     * @param before
     * @return
     */
    protected static boolean dispatchToBidiModelChangeWatcher(Object dataModel, View eventOrigin, String propertyChain,Object oldValue, Object newValue , boolean before ) {
        HashMap<String, HashSet<BidiModelChangeWatcher>> watcherOnOneInstance = modelWatchers.get(dataModel);
        if(watcherOnOneInstance == null) {
            //no watcher
            return true;
        }
        //as a default, apply setter....
        boolean toReturn = true;
        TreeSet<String> sortedKeysToNotify = new TreeSet<String>() ;
        for (Map.Entry<String, HashSet<BidiModelChangeWatcher>> entry : watcherOnOneInstance.entrySet()) {
            //test if propertyChain is child of listened property
            //note : indexed or mapped properties will disturb this simple detection.
            if(propertyChain.startsWith(entry.getKey()) || entry.getKey().equals(BidiModelChangeWatcher.ALL_PROPERTIES)) {
                //need to notify
                //we collect keys in alphabetic order to notify from deeper watcher to less specific
                sortedKeysToNotify.add(entry.getKey());
            }
        }
        //doing the notification
        for(String key : sortedKeysToNotify.descendingSet()) {
            for(BidiModelChangeWatcher watcher : watcherOnOneInstance.get(key)) {
                if(before) {
                    try {
                        toReturn = watcher.beforeUpdate(eventOrigin, dataModel, propertyChain, oldValue, newValue) && toReturn;
                    }
                    catch(Throwable e ) {
                        Log.i(TAG, "Exception during ModelChangeWatcherNotification " , e ) ;
                    }
                }
                else {
                    try{
                        watcher.afterUpdate(eventOrigin, dataModel, propertyChain, oldValue, newValue) ;
                        Log.d(TAG, "[NOTIFIED] " + watcher.getClass().getSimpleName() + dataModel.getClass().getSimpleName() + "." + propertyChain) ;
                    }
                    catch(Throwable e ) {
                        Log.i(TAG, "Exception during ModelChangeWatcherNotification " , e ) ;
                    }
                }
            }
        }
        return toReturn ;
    }


}

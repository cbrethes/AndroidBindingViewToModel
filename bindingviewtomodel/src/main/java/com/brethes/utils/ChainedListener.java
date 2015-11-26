package com.brethes.utils;

import android.os.Build;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by tof on 19/11/2015.
 */
public class ChainedListener implements View.OnClickListener , CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    protected static final String TAG = "ChainedListener" ;
    protected LinkedHashSet listeners = new LinkedHashSet();

    /** Found on http://stackoverflow.com/questions/11186960/getonclicklistener-in-android-views
     *  thks to http://stackoverflow.com/users/321697/kcoppock
     * Returns the current View.OnClickListener for the given View
     * @param view the View whose click listener to retrieve
     * @return the View.OnClickListener attached to the view; null if it could not be retrieved
     */
    protected static View.OnClickListener getOnClickListener(View view) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getOnClickListenerV14(view);
        } else {
            return getOnClickListenerV(view);
        }
    }


    public  static CompoundButton.OnCheckedChangeListener getOnCheckedChangeListenerOnCompoundButton(CompoundButton view) {
        CompoundButton.OnCheckedChangeListener retrievedListener = null;
        String viewStr = "android.widget.CompoundButton";
        Field field;

        try {
            field = Class.forName(viewStr).getDeclaredField("mOnCheckedChangeListener");
            retrievedListener = (CompoundButton.OnCheckedChangeListener) field.get(view);
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }
    public  static RadioGroup.OnCheckedChangeListener getOnCheckedChangeListenerOnCompoundButton(RadioGroup view) {
        RadioGroup.OnCheckedChangeListener retrievedListener = null;
        String viewStr = "android.widget.CompoundButton";
        Field field;

        try {
            field = Class.forName(viewStr).getDeclaredField("mOnCheckedChangeListener");
            retrievedListener = (RadioGroup.OnCheckedChangeListener) field.get(view);
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }
    /**
     * Found on http://stackoverflow.com/questions/11186960/getonclicklistener-in-android-views
     *  thks to http://stackoverflow.com/users/321697/kcoppock
     *
     *  Used for APIs lower than ICS (API 14)
     */
    private  static View.OnClickListener getOnClickListenerV(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        Field field;

        try {
            field = Class.forName(viewStr).getDeclaredField("mOnClickListener");
            retrievedListener = (View.OnClickListener) field.get(view);
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    /** Found on http://stackoverflow.com/questions/11186960/getonclicklistener-in-android-views
     *  thks to http://stackoverflow.com/users/321697/kcoppock

     * Used for new ListenerInfo class structure used beginning with API 14 (ICS)
     */
    private static View.OnClickListener getOnClickListenerV14(View view) {
        View.OnClickListener retrievedListener = null;
        String viewStr = "android.view.View";
        String lInfoStr = "android.view.View$ListenerInfo";

        try {
            Field listenerField = Class.forName(viewStr).getDeclaredField("mListenerInfo");
            Object listenerInfo = null;

            if (listenerField != null) {
                listenerField.setAccessible(true);
                listenerInfo = listenerField.get(view);
            }

            Field clickListenerField = Class.forName(lInfoStr).getDeclaredField("mOnClickListener");

            if (clickListenerField != null && listenerInfo != null) {
                retrievedListener = (View.OnClickListener) clickListenerField.get(listenerInfo);
            }
        } catch (NoSuchFieldException ex) {
            Log.e("Reflection", "No Such Field.");
        } catch (IllegalAccessException ex) {
            Log.e("Reflection", "Illegal Access.");
        } catch (ClassNotFoundException ex) {
            Log.e("Reflection", "Class Not Found.");
        }

        return retrievedListener;
    }

    public static View.OnClickListener addOnClickListenertoView(View view, View.OnClickListener newListener) {
        View.OnClickListener existingListener = getOnClickListener(view) ;
        return createChain(existingListener,newListener) ;
    }
    public static CompoundButton.OnCheckedChangeListener addOnCheckedChangeListener(CompoundButton view, CompoundButton.OnCheckedChangeListener newListener) {

        CompoundButton.OnCheckedChangeListener existingListener = getOnCheckedChangeListenerOnCompoundButton(view);
        CompoundButton.OnCheckedChangeListener listener =  createChain(existingListener,newListener) ;
        view.setOnCheckedChangeListener(listener);
        return listener;
    }
    public static RadioGroup.OnCheckedChangeListener addOnCheckedChangeListener(RadioGroup view, RadioGroup.OnCheckedChangeListener newListener) {

        RadioGroup.OnCheckedChangeListener existingListener = getOnCheckedChangeListenerOnCompoundButton(view);
        RadioGroup.OnCheckedChangeListener listener =  createChain(existingListener, newListener) ;
        view.setOnCheckedChangeListener(listener);
        return listener;
    }
    public static <U>  U createChain(U existingListener, U newListener ) {
        if(existingListener == null ) {
            return newListener ;
        }
        if(newListener == null) {
            return existingListener ;
        }
        if(existingListener instanceof ChainedListener) {
            ChainedListener chainedListener = (ChainedListener) existingListener;
            return (U) chainedListener.add(newListener);
        }
        else {
            //existingListener is not a ChainedListener  ;
            ChainedListener toReturn = new ChainedListener() ;
            toReturn.add(existingListener).add(newListener) ;
            return (U) toReturn;
        }
    }

    public ChainedListener add(Object newListener) {
        if(newListener instanceof ChainedListener  ) {
            return  addAll(((ChainedListener)newListener).listeners);
        }
        else {
            this.listeners.add(newListener);
            return this ;
        }
    }

    public ChainedListener addAll(Collection listeners) {
        this.listeners.addAll(listeners) ;
        return this ;
    }
    public boolean remove(Object listener) {
        if(listener instanceof ChainedListener  ) {
            return  removeAll(((ChainedListener) listener).listeners);
        }
        else {
            return this.listeners.remove(listener);
        }
    }

    private boolean removeAll(Collection listeners) {
        return this.listeners.removeAll(listeners) ;
    }

    @Override //View.OnClickListener
    public void onClick(View v) {
        for (Object listener: listeners  ) {
            ((View.OnClickListener)listener).onClick(v) ;
        }
    }

    @Override //CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for (Object listener: listeners  ) {
            ((CompoundButton.OnCheckedChangeListener)listener).onCheckedChanged(buttonView, isChecked) ;
        }

    }
    @Override //RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup buttonView,@IdRes int checkedId) {
        for (Object listener: listeners  ) {
            ((RadioGroup.OnCheckedChangeListener)listener).onCheckedChanged(buttonView, checkedId) ;
        }

    }
}

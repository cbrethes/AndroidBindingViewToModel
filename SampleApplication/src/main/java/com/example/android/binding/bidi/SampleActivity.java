/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.binding.bidi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.brethes.utils.BidiBinderManager;
import com.brethes.utils.BidiModelChangeWatcher;
import com.example.android.binding.bidi.data.MyPersistentPrefs;
import com.example.android.binding.bidi.databinding.SampleActivyBinding;


public class SampleActivity extends ActionBarActivity implements  BidiModelChangeWatcher{
    private static final String TAG  = "SampleActivity" ;
    SampleActivyBinding binding  ;

    protected MyPersistentPrefs folderPrefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sample_activity);
        //setContentView(R.layout.sample_activity);
        if(folderPrefs == null) {
            folderPrefs = new MyPersistentPrefs();
            //add dummy values
            folderPrefs.setCleanEmptyfolder(true);
            folderPrefs.setDisplayName("my pretty label");
            folderPrefs.setUsername("Mr Foo");
            //will be set using android databinding library, see sample_activity.xml folderPrefs.setPassword("1234 like usual");
            folderPrefs.setFolderpath("smb://myserver/folder/sub/");
        }
        binding.setMyprefs(folderPrefs);
        //view to model
        BidiBinderManager.bind(binding.textViewChemin,binding.getMyprefs(),"folderpath");
        BidiBinderManager.bind(binding.editTextUsername,binding.getMyprefs(),"username");
        BidiBinderManager.bind(binding.editTextPassword,binding.getMyprefs(),"password");
        BidiBinderManager.bind(binding.checkBoxCleanFilesWithoutVideo,binding.getMyprefs(),"cleanFilesWithoutVideo");
        BidiBinderManager.bind(binding.checkBoxCleanVideosamples,binding.getMyprefs(),"cleanVideosamples");
        BidiBinderManager.bind(binding.checkBoxCleanRecursefolders,binding.getMyprefs(),"cleanRecursefolders");
        BidiBinderManager.bind(binding.checkBoxCleanEmptyfolder,binding.getMyprefs(),"cleanEmptyfolder");
        BidiBinderManager.bind(binding.checkBoxCleanNamesreplacedotswithspaces,binding.getMyprefs(),"cleanNamesreplacedotswithspaces");
        //callback to save folder
        BidiBinderManager.registerDataModelChangeWatcher(binding.getMyprefs(),BidiModelChangeWatcher.ALL_PROPERTIES,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void onClick_button_valider(View view)
    {
        TextView logTextView = binding.MainActivityLogWindow;
        try {
                logTextView.setText("successfully accessed to " + binding.textViewChemin.getText().toString());
        }
        catch (Exception e ) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            logTextView.setText(e.getMessage()) ;
        }
    }
    @Override
    public boolean beforeUpdate(View eventOrigin, Object dataModel, String propertyChain, Object oldValue, Object newValue) {
        return true ;
    }

    @Override
    public void afterUpdate(View eventOrigin, Object dataModel, String propertyChain, Object oldValue, Object newValue) {
        Toast.makeText(this,"save occured, because " + propertyChain + " has changed from " + oldValue + " to " + newValue + " thanks to listener :)",Toast.LENGTH_SHORT).show();
    }

}

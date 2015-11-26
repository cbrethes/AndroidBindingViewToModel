package com.example.android.binding.bidi.databinding;
import com.example.android.binding.bidi.R;
import com.example.android.binding.bidi.BR;
import android.view.View;
public class SampleActivyBinding extends android.databinding.ViewDataBinding {
    
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.username_label, 10);
        sViewsWithIds.put(R.id.textView_password, 11);
        sViewsWithIds.put(R.id.textView_displayName, 12);
        sViewsWithIds.put(R.id.button_valider_repertoire, 13);
        sViewsWithIds.put(R.id.MainActivityLogWindow, 14);
    }
    // views
    public final android.widget.TextView MainActivityLogWindow;
    public final android.widget.Button buttonValiderRepertoire;
    public final android.widget.Switch checkBoxCleanEmptyfolder;
    public final android.widget.Switch checkBoxCleanFilesWithoutVideo;
    public final android.widget.Switch checkBoxCleanNamesreplacedotswithspaces;
    public final android.widget.Switch checkBoxCleanRecursefolders;
    public final android.widget.Switch checkBoxCleanVideosamples;
    public final android.widget.EditText editTextDisplayName;
    public final android.widget.EditText editTextPassword;
    public final android.widget.EditText editTextUsername;
    public final android.widget.ScrollView scrollView;
    public final android.widget.EditText textViewChemin;
    public final android.widget.TextView textViewDisplayName;
    public final android.widget.TextView textViewPassword;
    public final android.widget.TextView usernameLabel;
    // variables
    private com.example.android.binding.bidi.data.MyPersistentPrefs mMyprefs;
    // values
    // listeners
    
    public SampleActivyBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.MainActivityLogWindow = (android.widget.TextView) bindings[14];
        this.buttonValiderRepertoire = (android.widget.Button) bindings[13];
        this.checkBoxCleanEmptyfolder = (android.widget.Switch) bindings[8];
        this.checkBoxCleanEmptyfolder.setTag(null);
        this.checkBoxCleanFilesWithoutVideo = (android.widget.Switch) bindings[5];
        this.checkBoxCleanFilesWithoutVideo.setTag(null);
        this.checkBoxCleanNamesreplacedotswithspaces = (android.widget.Switch) bindings[9];
        this.checkBoxCleanNamesreplacedotswithspaces.setTag(null);
        this.checkBoxCleanRecursefolders = (android.widget.Switch) bindings[7];
        this.checkBoxCleanRecursefolders.setTag(null);
        this.checkBoxCleanVideosamples = (android.widget.Switch) bindings[6];
        this.checkBoxCleanVideosamples.setTag(null);
        this.editTextDisplayName = (android.widget.EditText) bindings[4];
        this.editTextDisplayName.setTag(null);
        this.editTextPassword = (android.widget.EditText) bindings[3];
        this.editTextPassword.setTag(null);
        this.editTextUsername = (android.widget.EditText) bindings[2];
        this.editTextUsername.setTag(null);
        this.scrollView = (android.widget.ScrollView) bindings[0];
        this.scrollView.setTag(null);
        this.textViewChemin = (android.widget.EditText) bindings[1];
        this.textViewChemin.setTag(null);
        this.textViewDisplayName = (android.widget.TextView) bindings[12];
        this.textViewPassword = (android.widget.TextView) bindings[11];
        this.usernameLabel = (android.widget.TextView) bindings[10];
        setRootTag(root);
        invalidateAll();
    }
    
    @Override
    public void invalidateAll() {
        synchronized(this) {
            mDirtyFlags = 0x2L;
        }
        requestRebind();
    }
    
    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
            case BR.myprefs :
                setMyprefs((com.example.android.binding.bidi.data.MyPersistentPrefs) variable);
                return true;
        }
        return false;
    }
    
    public void setMyprefs(com.example.android.binding.bidi.data.MyPersistentPrefs myprefs) {
        this.mMyprefs = myprefs;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        super.requestRebind();
    }
    public com.example.android.binding.bidi.data.MyPersistentPrefs getMyprefs() {
        return mMyprefs;
    }
    
    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }
    
    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        boolean cleanRecursefoldersM = false;
        boolean PasswordMyprefsObjec = false;
        boolean cleanFilesWithoutVid = false;
        boolean cleanNamesreplacedot = false;
        java.lang.String folderpathMyprefs = null;
        boolean UsernameMyprefsObjec = false;
        com.example.android.binding.bidi.data.MyPersistentPrefs myprefs = mMyprefs;
        java.lang.String passwordMyprefs = null;
        java.lang.String PasswordMyprefsObjec1 = null;
        java.lang.String displayNameMyprefs = null;
        boolean cleanVideosamplesMyp = false;
        boolean cleanEmptyfolderMypr = false;
        java.lang.String usernameMyprefs = null;
        java.lang.String UsernameMyprefsObjec1 = null;
    
        if ((dirtyFlags & 0x3L) != 0) {
            // read myprefs~
            myprefs = myprefs;
        
            if (myprefs != null) {
                // read cleanRecursefolders~.~myprefs~
                cleanRecursefoldersM = myprefs.isCleanRecursefolders();
                // read cleanFilesWithoutVideo~.~myprefs~
                cleanFilesWithoutVid = myprefs.isCleanFilesWithoutVideo();
                // read cleanNamesreplacedotswithspaces~.~myprefs~
                cleanNamesreplacedot = myprefs.isCleanNamesreplacedotswithspaces();
                // read folderpath~.~myprefs~
                folderpathMyprefs = myprefs.getFolderpath();
                // read password~.~myprefs~
                passwordMyprefs = myprefs.getPassword();
                // read displayName~.~myprefs~
                displayNameMyprefs = myprefs.getDisplayName();
                // read cleanVideosamples~.~myprefs~
                cleanVideosamplesMyp = myprefs.isCleanVideosamples();
                // read cleanEmptyfolder~.~myprefs~
                cleanEmptyfolderMypr = myprefs.isCleanEmptyfolder();
                // read username~.~myprefs~
                usernameMyprefs = myprefs.getUsername();
            }
        
            // read ==~password~.~myprefs~~Objectnull
            PasswordMyprefsObjec = passwordMyprefs==null;
            // read ==~username~.~myprefs~~Objectnull
            UsernameMyprefsObjec = usernameMyprefs==null;
            if((dirtyFlags & 0x3L) != 0) {
                if (PasswordMyprefsObjec) {
                    dirtyFlags |= 0x8L;
                } else {
                    dirtyFlags |= 0x4L;
                }}
            if((dirtyFlags & 0x3L) != 0) {
                if (UsernameMyprefsObjec) {
                    dirtyFlags |= 0x20L;
                } else {
                    dirtyFlags |= 0x10L;
                }}
        }
        // batch finished
    
        if ((dirtyFlags & 0x3L) != 0) {
            // read ?:==~password~.~myprefs~~Objectnull~String"1234 is secure"~password~.~myprefs~
            PasswordMyprefsObjec1 = PasswordMyprefsObjec ? "1234 is secure" : passwordMyprefs;
            // read ?:==~username~.~myprefs~~Objectnull~String"dummy user"~username~.~myprefs~
            UsernameMyprefsObjec1 = UsernameMyprefsObjec ? "dummy user" : usernameMyprefs;
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1
            this.checkBoxCleanEmptyfolder.setChecked(cleanEmptyfolderMypr);
            this.checkBoxCleanFilesWithoutVideo.setChecked(cleanFilesWithoutVid);
            this.checkBoxCleanNamesreplacedotswithspaces.setChecked(cleanNamesreplacedot);
            this.checkBoxCleanRecursefolders.setChecked(cleanRecursefoldersM);
            this.checkBoxCleanVideosamples.setChecked(cleanVideosamplesMyp);
            this.editTextDisplayName.setText(displayNameMyprefs);
            this.editTextPassword.setText(PasswordMyprefsObjec1);
            this.editTextUsername.setText(UsernameMyprefsObjec1);
            this.textViewChemin.setText(folderpathMyprefs);
        }
    }
    // Listener Stub Implementations
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    
    public static SampleActivyBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static SampleActivyBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<SampleActivyBinding>inflate(inflater, com.example.android.binding.bidi.R.layout.sample_activity, root, attachToRoot, bindingComponent);
    }
    public static SampleActivyBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static SampleActivyBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.example.android.binding.bidi.R.layout.sample_activity, null, false), bindingComponent);
    }
    public static SampleActivyBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static SampleActivyBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/sample_activity_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new SampleActivyBinding(bindingComponent, view);
    }
}
    /* flag mapping
        flag 0: myprefs~
        flag 1: INVALIDATE ANY
        flag 2: ?:==~password~.~myprefs~~Objectnull~String"1234 is secure"~password~.~myprefs~== false
        flag 3: ?:==~password~.~myprefs~~Objectnull~String"1234 is secure"~password~.~myprefs~== true
        flag 4: ?:==~username~.~myprefs~~Objectnull~String"dummy user"~username~.~myprefs~== false
        flag 5: ?:==~username~.~myprefs~~Objectnull~String"dummy user"~username~.~myprefs~== true
    flag mapping end*/
    //end
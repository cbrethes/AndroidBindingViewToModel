/*
 * Copyright (c) 2015. Christophe BRETHES.
 * You can copy, modify, use this for your application and if this stuff is usefull for you,
 *  just let me know 'cbrethes at gmail.com' http://devbreezecorner.blogspot.fr/
 */

package com.example.android.binding.bidi.data;


public class MyPersistentPrefs {
    //the id used as prefsname
    private String id ;
    private String folderpath ;
    private String username ;
    private String password ;


    private String displayName ;

    private boolean cleanFilesWithoutVideo = false ;
    private boolean cleanVideosamples = false ;
    private boolean cleanRecursefolders = false  ;
    private boolean cleanEmptyfolder = false ;
    private boolean cleanNamesreplacedotswithspaces = false ;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolderpath() {
        return folderpath;
    }

    public void setFolderpath(String folderpath) {
        this.folderpath = folderpath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCleanEmptyfolder() {
        return cleanEmptyfolder;
    }

    public void setCleanEmptyfolder(boolean cleanEmptyfolder) {
        this.cleanEmptyfolder = cleanEmptyfolder;
    }

    public boolean isCleanFilesWithoutVideo() {
        return cleanFilesWithoutVideo;
    }

    public void setCleanFilesWithoutVideo(boolean cleanFilesWithoutVideo) {
        this.cleanFilesWithoutVideo = cleanFilesWithoutVideo;
    }

    public boolean isCleanNamesreplacedotswithspaces() {
        return cleanNamesreplacedotswithspaces;
    }

    public void setCleanNamesreplacedotswithspaces(boolean cleanNamesreplacedotswithspaces) {
        this.cleanNamesreplacedotswithspaces = cleanNamesreplacedotswithspaces;
    }

    public boolean isCleanRecursefolders() {
        return cleanRecursefolders;
    }

    public void setCleanRecursefolders(boolean cleanRecursefolders) {
        this.cleanRecursefolders = cleanRecursefolders;
    }

    public boolean isCleanVideosamples() {
        return cleanVideosamples;
    }

    public void setCleanVideosamples(boolean cleanVideosamples) {
        this.cleanVideosamples = cleanVideosamples;
    }

}

package com.chetan.project2;

/**
 * Created by chetan on 2/25/2018.
 */

public class SongItem {
    String SongTitle;
    String BandName;
    String WikiUrl;
    String VideoUrl;



    public String getBandWiki() {
        return BandWiki;
    }

    public void setBandWiki(String bandWiki) {
        BandWiki = bandWiki;
    }

    String BandWiki;


    public void setSongTitle(String songTitle) {
        SongTitle = songTitle;
    }

    public void setBandName(String bandName) {
        BandName = bandName;
    }

    public void setWikiUrl(String wikiUrl) {
        WikiUrl = wikiUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getSongTitle() {
        return SongTitle;
    }

    public String getBandName() {
        return BandName;
    }

    public String getWikiUrl() {
        return WikiUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }




    public SongItem(String songTitle, String bandName, String wikiUrl, String videoUrl,String bandWiki) {
        SongTitle = songTitle;
        BandName = bandName;
        WikiUrl = wikiUrl;
        VideoUrl = videoUrl;
        BandWiki = bandWiki;
    }


}

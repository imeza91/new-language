package com.example.android.miwok;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    // handles playback for all the sound file that
    //will be assigned to it when the user click on a word
    private MediaPlayer mMediaPlayer;

    //This variable is passed to setOnComp.. when audio is done playing
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "әpә",R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa",R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi",R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune",R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi",R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("young brother", "chalitti",R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe",R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("grandmother", "ama",R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa",R.drawable.family_grandfather, R.raw.family_grandfather));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_listyout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //make sure to release the media player before we play anything
                releaseMediaPlayer();

                //identify the word being clicked on
                Word word = words.get(position);

                //set mMediaPlayer with the current word sound
                mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId() );
                //play it
                mMediaPlayer.start();

                //set up a onCompletion listener so that we can release media player
                // when its done playing the audio
                mMediaPlayer.setOnCompletionListener( mOnCompletionListener);

            }
        });

        return rootView;
    }

    //release mMediaPlayer when activity stopped
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();

    }

    private void releaseMediaPlayer(){
        //if mMediaPlayer has audio attach to it, release it becuase it might be playing
        if (mMediaPlayer != null){
            mMediaPlayer.release();
        }

        //setting mMediaPlayer to null ensure that it cannot play audio
        mMediaPlayer = null;
    }
}

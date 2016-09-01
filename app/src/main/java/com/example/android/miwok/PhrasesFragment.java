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
public class PhrasesFragment extends Fragment {

    MediaPlayer mMediaPlayer;

    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer(); //this method release the media player.
        }
    };

    //auto generated constructor
    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container, false);

        //create list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling", "michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good", "kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming", "әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming", "hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming", "әәnәm",R.raw.phrase_im_coming));
        words.add(new Word("Lets go", "yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here", "әnni'nem",R.raw.phrase_come_here));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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

                //set mMediaPlayer with the current word
                mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId() );

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

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

}

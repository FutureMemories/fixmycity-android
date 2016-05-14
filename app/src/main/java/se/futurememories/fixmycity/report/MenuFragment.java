package se.futurememories.fixmycity.report;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.futurememories.fixmycity.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends android.app.Fragment {

    @BindView(R.id.question_btn)
    ImageButton questionBtn;
    @BindView(R.id.suggest_btn)
    ImageButton suggestBtn;
    @BindView(R.id.dirty_btn)
    ImageButton dirtyBtn;
    @BindView(R.id.praise_btn)
    ImageButton praiseBtn;
    @BindView(R.id.broken_btn)
    ImageButton brokenBtn;
    @BindView(R.id.problem_btn)
    ImageButton problemBtn;
    @BindView(R.id.question_text)
    TextView questionText;
    @BindView(R.id.suggest_text)
    TextView suggestText;
    @BindView(R.id.dirty_text)
    TextView dirtyText;
    @BindView(R.id.praise_text)
    TextView praiseText;
    @BindView(R.id.broken_text)
    TextView brokenText;
    @BindView(R.id.problem_text)
    TextView problemText;

    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionBtn.setTransitionName("icon");
                questionText.setTransitionName("text");
                AddDetailsFragment addDetailsFragment = AddDetailsFragment.newInstance(questionText.getText().toString(), R.drawable.question_button);
                addDetailsFragment.setSharedElementEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(R.transition.change));
                setSharedElementEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(R.transition.change));
                addDetailsFragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_right));
                setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_left));
                getFragmentManager().beginTransaction()
                        .addSharedElement(questionBtn, "icon")
                        .addSharedElement(questionText, "text")
                        .remove(MenuFragment.this)
                        .add(R.id.map, addDetailsFragment).addToBackStack(null).commit();


            }
        });


        brokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dirtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        praiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        problemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemBtn.setTransitionName("icon");
                problemText.setTransitionName("text");
                AddDetailsFragment addDetailsFragment = AddDetailsFragment.newInstance(problemText.getText().toString(), R.drawable.problem_button);
                addDetailsFragment.setSharedElementEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(R.transition.change));
                setSharedElementEnterTransition(TransitionInflater.from(
                        getActivity()).inflateTransition(R.transition.change));
                addDetailsFragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_right));
                setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_left));
                getFragmentManager().beginTransaction()
                        .addSharedElement(problemBtn, "icon")
                        .addSharedElement(problemText, "text")
                        .remove(MenuFragment.this).addToBackStack(null)
                        .add(R.id.map, addDetailsFragment).addToBackStack(null).commit();

            }
        });
        suggestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

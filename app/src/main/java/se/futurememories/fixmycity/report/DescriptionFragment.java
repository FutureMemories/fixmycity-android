package se.futurememories.fixmycity.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.futurememories.fixmycity.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends android.app.Fragment {

    @BindView(R.id.save_btn)
    Button save;
    @BindView(R.id.comment_field)
    EditText commentField;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("desc", commentField.getText().toString());
                getFragmentManager().popBackStack();
                getTargetFragment().onActivityResult(getTargetRequestCode(), 1449, intent);
            }
        });
    }
}

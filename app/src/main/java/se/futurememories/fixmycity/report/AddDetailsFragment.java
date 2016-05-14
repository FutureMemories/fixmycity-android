package se.futurememories.fixmycity.report;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import se.futurememories.fixmycity.R;
import se.futurememories.fixmycity.model.ApiManager;

public class AddDetailsFragment extends android.app.Fragment {

    @BindView(R.id.type_img)
    ImageButton imageButton;
    @BindView(R.id.type_text)
    TextView mtext;
    @BindView(R.id.category_val)
    TextView cateVal;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.camera_btn)
    ImageButton camera;
    @BindView(R.id.comment_btn)
    TextView commentBtn;
    @BindView(R.id.send_btn)
    Button sendBtn;
    private String mCurrentPhotoPath;
    private String mPath;

//    private OnFragmentInteractionListener mListener;

    public AddDetailsFragment() {
        // Required empty public constructor
    }
    private int img;
    private String description;
    private String text;
    private String category;
    private Uri fileUri;


    // TODO: Rename and change types and number of parameters
    public static AddDetailsFragment newInstance(String text, int img) {
        AddDetailsFragment fragment = new AddDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("img", img);
        args.putString("text", text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            img = getArguments().getInt("img");
            text = getArguments().getString("text");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());
        imageButton.setBackground(getActivity().getDrawable(img));
        mtext.setText(text);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescriptionFragment fragment = new DescriptionFragment();
                fragment.setTargetFragment(AddDetailsFragment.this, 1448);
                fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_right));
                setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_left));
                getFragmentManager().beginTransaction()
                        .remove(AddDetailsFragment.this).addToBackStack(null)
                        .add(R.id.map, fragment).addToBackStack(null).commit();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] split = mCurrentPhotoPath.split("file:");

                File file = new File(split[1]);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                try {
                    Log.i("TAG", String.valueOf(fileBody.contentLength()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ApiManager.getRestInstance().postPhoto(fileBody, mtext.getText().toString(), category, 51.21, 53.321)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Response<Integer>>() {
                    @Override
                    public void call(Response<Integer> voidResponse) {
                        Log.i("TAG", "Onnext");
                        while (getFragmentManager().getBackStackEntryCount() > 0) {
                            getFragmentManager().popBackStackImmediate();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("TAG", "onerror");
                        throwable.printStackTrace();
                        while (getFragmentManager().getBackStackEntryCount() > 0) {
                            getFragmentManager().popBackStackImmediate();
                        }


                    }
                });
            }
        });
    }

    @OnClick(R.id.category_btn)
    public void categoryClicked() {
        CategoryFragment fragment = CategoryFragment.newInstance(mtext.getText().toString());
        fragment.setTargetFragment(this, 1337);
        fragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_right));
        setExitTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.slide_left));
        getFragmentManager().beginTransaction()
                .remove(AddDetailsFragment.this).addToBackStack(null)
                .add(R.id.map, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            getArguments().putString("uri", mCurrentPhotoPath);

            photo.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(getActivity()).load(mCurrentPhotoPath).fit().into(photo);
                    photo.setVisibility(View.VISIBLE);
                    camera.setVisibility(View.GONE);
                }
            }, 100);

        }
        if (requestCode == 1337 && resultCode == 1338) {
        if (data != null) {
            category = data.getStringExtra("cat");
            getArguments().putString("cate", category);
            Log.i("TAG", "category is: " + data.getStringExtra("cat"));
            cateVal.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cateVal.setText(category);
                }
            }, 100);
        }
        }
        if (requestCode == 1448 && resultCode == 1449) {
            description = data.getStringExtra("desc");
            getArguments().putString("descu", description);

            commentBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    commentBtn.setCompoundDrawables(null, null, null, null);
                    commentBtn.setGravity(Gravity.TOP);
                    commentBtn.setText(description);
                }
            }, 100);
        }
    }

    @OnClick(R.id.camera_btn)
    public void takePhoto() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            fileUri = MediaUtil.getOutputMediaFileUri(MediaUtil.MEDIA_TYPE_IMAGE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i("Fuck", "me");
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 12);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    @Override
    public void onPause() {
        super.onPause();
        Bundle args = getArguments();
        if (mCurrentPhotoPath != null) {
            args.putString("uri", mCurrentPhotoPath);
        }
        if (category != null) {
            args.putString("cate", category);
        }
        if (description != null) {
            args.putString("descu", description);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle args) {
        super.onSaveInstanceState(args);
        if (mCurrentPhotoPath != null) {
            args.putString("uri", mCurrentPhotoPath);
        }
        if (category != null) {
            args.putString("cate", category);
        }
        if (description != null) {
            args.putString("descu", description);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle args = getArguments();
        if (mCurrentPhotoPath != null) {
            args.putString("uri", mCurrentPhotoPath);
        }
        if (category != null) {
            args.putString("cate", category);
        }
        if (description != null) {
            args.putString("descu", description);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Bundle savedState = getArguments();
        if (getArguments() != null) {
            mCurrentPhotoPath = savedState.getString("uri", "");
            category = savedState.getString("cate", "Välj en kategori");
            description = savedState.getString("descu","Lägg till kommentar");
            setData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPhotoPath = savedInstanceState.getString("uri", "");
            category = savedInstanceState.getString("cate", "Välj en kategori");
            description = savedInstanceState.getString("descu","Lägg till kommentar");
            setData();

        }
    }

    private void setData() {
        if (!commentBtn.equals("Lägg till kommentar")) {
            commentBtn.setCompoundDrawables(null, null, null, null);
            commentBtn.setGravity(Gravity.TOP);
            commentBtn.setText(description);
        }
        cateVal.setText(category);
        if (mCurrentPhotoPath != null && !mCurrentPhotoPath.equals("")) {
            Picasso.with(getActivity()).load(mCurrentPhotoPath).fit().into(photo);
            photo.setVisibility(View.VISIBLE);
            camera.setVisibility(View.GONE);
        }
    }
}

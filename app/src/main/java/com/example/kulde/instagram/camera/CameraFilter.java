package com.example.kulde.instagram.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.kulde.instagram.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CameraFilter.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CameraFilter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFilter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    private static Bundle imageArg;

    public CameraFilter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment CameraFilter.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraFilter newInstance(Bundle args) {
        //String recieve = getArguments().getString("Image");

        return new CameraFilter();
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        ImageView imageEdit = (ImageView) view.findViewById(R.id.picture_view);
        ImageButton backToTake = (ImageButton) view.findViewById(R.id.back);
        view.findViewById(R.id.yellow);
        view.findViewById(R.id.origin);
        view.findViewById(R.id.black);
        view.findViewById(R.id.neon);
        String recieve = getArguments().getString("Image");
        Bitmap bitmap = BitmapFactory.decodeFile(recieve);
        imageEdit.setImageBitmap(bitmap);

        backToTake.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, CameraFragment.newInstance())
                        .commit();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_filter, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

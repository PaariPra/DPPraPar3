package com.whatsycrrop.dpmaker.photoeditor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.whatsycrrop.dpmaker.R;


public class StickerBSFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == 5) {
                StickerBSFragment.this.dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
    private StickerListener mStickerListener;


    public interface StickerListener {
        void onStickerClick(Bitmap bitmap);
    }

    public void setStickerListener(StickerListener stickerListener) {
        this.mStickerListener = stickerListener;
    }

    @Override
    @SuppressLint({"RestrictedApi", "ResourceType"})
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        if (behavior != null && (behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(17170445));
        RecyclerView rvEmoji = (RecyclerView) contentView.findViewById(R.id.rvEmoji);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvEmoji.setLayoutManager(gridLayoutManager);
        StickerAdapter stickerAdapter = new StickerAdapter();
        rvEmoji.setAdapter(stickerAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        int[] stickerList = new int[]{





                R.drawable.z_str_51, R.drawable.z_str_52, R.drawable.z_str_53,
                R.drawable.z_str_54, R.drawable.z_str_55, R.drawable.z_str_56,
                R.drawable.z_str_57, R.drawable.z_str_58, R.drawable.z_str_59,
                R.drawable.z_str_510,


                R.drawable.z_str_91, R.drawable.z_str_92, R.drawable.z_str_93,
                R.drawable.z_str_94, R.drawable.z_str_95, R.drawable.z_str_96,
                R.drawable.z_str_97, R.drawable.z_str_98, R.drawable.z_str_99,
                R.drawable.z_str_910,


                R.drawable.z_str_71, R.drawable.z_str_72, R.drawable.z_str_73,
                R.drawable.z_str_74, R.drawable.z_str_75, R.drawable.z_str_76,
                R.drawable.z_str_77, R.drawable.z_str_78, R.drawable.z_str_79,
                R.drawable.z_str_710,



                R.drawable.z_str_11, R.drawable.z_str_12, R.drawable.z_str_13,
                R.drawable.z_str_14, R.drawable.z_str_15, R.drawable.z_str_16,
                R.drawable.z_str_17, R.drawable.z_str_18, R.drawable.z_str_19,
                R.drawable.z_str_110,

                R.drawable.z_str_21, R.drawable.z_str_22, R.drawable.z_str_23,
                R.drawable.z_str24, R.drawable.z_str_25, R.drawable.z_str_26,
                R.drawable.z_str_27, R.drawable.z_str_28, R.drawable.z_str_29,
                R.drawable.z_str_210,

                R.drawable.z_str_1, R.drawable.z_str_2, R.drawable.z_str_3,
                R.drawable.z_str_4, R.drawable.z_str_5, R.drawable.z_str_6,
                R.drawable.z_str_7, R.drawable.z_str_8, R.drawable.z_str_9,
                R.drawable.z_str_10,





                R.drawable.z_str_41, R.drawable.z_str_42, R.drawable.z_str_43,
                R.drawable.z_str_44, R.drawable.z_str_45, R.drawable.z_str_46,
                R.drawable.z_str_47, R.drawable.z_str_48, R.drawable.z_str_49,
                R.drawable.z_str_410,



                R.drawable.z_str_61, R.drawable.z_str_62, R.drawable.z_str_63,
                R.drawable.z_str_64, R.drawable.z_str_65, R.drawable.z_str_66,
                R.drawable.z_str_67, R.drawable.z_str_68, R.drawable.z_str_69,
                R.drawable.z_str_610,



                R.drawable.z_str_81, R.drawable.z_str_82, R.drawable.z_str_83,
                R.drawable.z_str_84, R.drawable.z_str_85, R.drawable.z_str_86,
                R.drawable.z_str_87, R.drawable.z_str_88, R.drawable.z_str_89,
                R.drawable.z_str_810,





                R.drawable.z_str_31, R.drawable.z_str_32, R.drawable.z_str_33,
                R.drawable.z_str_34, R.drawable.z_str_35, R.drawable.z_str_36,
                R.drawable.z_str_37, R.drawable.z_str_38, R.drawable.z_str_39,
                R.drawable.z_str_310,


                R.drawable.z_atr_101, R.drawable.z_atr_102, R.drawable.z_atr_103,
                R.drawable.z_atr_104, R.drawable.z_atr_105, R.drawable.z_atr_106,
                R.drawable.z_atr_107, R.drawable.z_atr_108, R.drawable.z_atr_109,
                R.drawable.z_atr_1010,



                R.drawable.z_str_111, R.drawable.z_str_112, R.drawable.z_str_113,
                R.drawable.z_str_114, R.drawable.z_str_115, R.drawable.z_str_116,
                R.drawable.z_str_117, R.drawable.z_str_118, R.drawable.z_str_119,
                R.drawable.z_str_1110,





        };

        public StickerAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
            return new ViewHolder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.imgSticker.setImageResource(this.stickerList[position]);
        }

        @Override
        public int getItemCount() {
            return this.stickerList.length;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgSticker;

            ViewHolder(View itemView) {
                super(itemView);
                this.imgSticker = (ImageView) itemView.findViewById(R.id.imgSticker);
                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (StickerBSFragment.this.mStickerListener != null) {
                            StickerBSFragment.this.mStickerListener.onStickerClick(BitmapFactory.decodeResource(StickerBSFragment.this.getResources(), StickerAdapter.this.stickerList[ViewHolder.this.getLayoutPosition()]));
                        }
                        StickerBSFragment.this.dismiss();
                    }
                });
            }
        }
    }

    private String convertEmoji(String emoji) {
        try {
            int convertEmojiToInt = Integer.parseInt(emoji.substring(2), 16);
            String returnedEmoji = getEmojiByUnicode(convertEmojiToInt);
            return returnedEmoji;
        } catch (NumberFormatException e) {
            return "";
        }
    }

    private String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}

package com.ercasng.eze.igrmobile.AnimationDir;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


public class MyAnimation {
    public static void animate(RecyclerView.ViewHolder holder) {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(holder.itemView);
    }


    public static void animateButton(View view) {
        YoYo.with(Techniques.SlideInDown)
                .duration(4500)
                .playOn(view);
    }

    public static void animateHome(RecyclerView.ViewHolder holder) {
        YoYo.with(Techniques.SlideInDown)
                .duration(5000)
                .playOn(holder.itemView);
    }

    public static void animateHome(View view) {
        YoYo.with(Techniques.SlideInDown)
                .duration(5000)
                .playOn(view);
    }

    public static void animateRecycler(RecyclerView.ViewHolder holder) {
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(holder.itemView);
    }


    public static void animateProtocol(RecyclerView.ViewHolder holder) {
        YoYo.with(Techniques.BounceIn)
                .duration(1000)
                .playOn(holder.itemView);
    }
    public static void animateList(View view) {
        YoYo.with(Techniques.RollIn)
                .duration(1500)
                .playOn(view);
    }

    public static void animateFromLeft(View view) {
        YoYo.with(Techniques.SlideInLeft)
                .duration(1500)
                .playOn(view);
    }

    public static void animateFromRight(View view) {
        YoYo.with(Techniques.SlideInRight)
                .duration(1500)
                .playOn(view);
    }

    public static void animateFromTop(View view) {
        YoYo.with(Techniques.SlideInUp)
                .duration(1500)
                .playOn(view);
    }

    public static void animateFromDown(View view) {
        YoYo.with(Techniques.SlideInDown)
                .duration(1500)
                .playOn(view);
    }

    public static void animateHeader(View view) {
        YoYo.with(Techniques.FadeInRight)
                .duration(1500)
                .playOn(view);
    }

    public static void animateSplash(View view) {
        YoYo.with(Techniques.SlideInDown)
                .duration(1500)
                .playOn(view);
    }

    public static void animateEditText(View view) {
        YoYo.with(Techniques.Shake)
                .duration(1500)
                .playOn(view);
    }

    public static void animateButton2(View view) {
        YoYo.with(Techniques.BounceInUp)
                .duration(1500)
                .playOn(view);
    }
    public static void animateEditText2(View view) {
        YoYo.with(Techniques.RubberBand)
                .duration(1500)
                .playOn(view);
    }

    public static void animateImage(View view) {
        YoYo.with(Techniques.SlideInDown)
                .duration(3000)
                .playOn(view);

    }
}
